package com.od.library_management_system.repository;

import com.od.library_management_system.entity.Book;
import com.od.library_management_system.entity.Borrow;
import com.od.library_management_system.utils.ResponseUtility;
import com.od.library_management_system.utils.TranSqlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

import static com.od.library_management_system.enums.ResponseCode.ERR_LIB_0002;
import static com.od.library_management_system.utils.Constants.*;

@Repository
public class BorrowRepoImpl implements BorrowRepo{

    @Autowired
    private TranSqlServices tranSqlServices;

    @Autowired
    private BookRepo bookRepo;

    @Override
    public Integer createBorrow(Borrow borrow) {
        Map<String, Object> param = new HashMap<>();
        param.put(MEM_ID, borrow.getMemberId());
        param.put(BOOK_ID, borrow.getBookId());
        param.put(BORROW_DATE, LocalDate.now());
        param.put(DUE_DATE, borrow.getDueDate());

        String sql = "INSERT INTO borrow (member_id, book_id, borrowed_date, due_date) VALUES (:member_id, :book_id, :borrowed_date, :due_date)";
        if(isMemberExist(borrow.getMemberId()) && isBookExist(borrow.getBookId())) {
            return tranSqlServices.persist(sql, new MapSqlParameterSource(param));
        }
        return 0;
    }

    @Override
    public boolean isBorrowExist(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);
        String sQuery = "select count(id) from borrow where id=:id";

        return tranSqlServices.getInteger(sQuery, new MapSqlParameterSource(param)) > 0;
    }

    @Override
    public Integer updateBorrow(Borrow borrow) {
        if(!isBorrowExist(borrow.getId())) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BORROW, borrow.getId())
                    ));
        }

        Map<String, Object> param = new HashMap<>();
        param.put(ID, borrow.getId());

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE borrow SET ");

        boolean isFirstField = true;

        if (Objects.nonNull(borrow.getBorrowedDate())) {
            param.put(BORROW_DATE, borrow.getBorrowedDate());
            sql.append(isFirstField ? "" : ", ").append("borrowed_date = :borrowed_date");
            isFirstField = false;
        }

        if (Objects.nonNull(borrow.getDueDate())) {
            param.put(DUE_DATE, borrow.getDueDate());
            sql.append(isFirstField ? "" : ", ").append("due_date = :due_date");
            isFirstField = false;
        }

        if (Objects.nonNull(borrow.getMemberId()) && isMemberExist(borrow.getMemberId())) {
            param.put(MEM_ID, borrow.getMemberId());
            sql.append(isFirstField ? "" : ", ").append("member_id = :member_id");
            isFirstField = false;
        }

        if (Objects.nonNull(borrow.getBookId()) && isBookExist(borrow.getBookId())) {
            param.put(BOOK_ID, borrow.getBookId());
            sql.append(isFirstField ? "" : ", ").append("book_id = :book_id");
            isFirstField = false;
        }

        sql.append(" WHERE id = :id");

        return tranSqlServices.persist(sql.toString(), new MapSqlParameterSource(param));
    }

    @Override
    public Integer deleteBorrowById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);

        if(!isBorrowExist(id)) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BORROW, id)
                    ));
        }

        String sql = "delete from borrow where id = :id";
        return tranSqlServices.updateDelete(sql, param);
    }

    @Override
    public Map<String, Object> getBorrowById(Long id) {
        if(!isBorrowExist(id)) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BORROW, id)
                    ));
        }

        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);

        StringBuilder sql = new StringBuilder();
        sql.append("select ")
                .append("b.id AS borrow_id, ")
                .append("b.member_id as member_id, ")
                .append("m.name AS member_name, ")
                .append("m.phone AS member_phone, ")
                .append("bk.id AS book_id, ")
                .append("bk.title AS book_title, ")
                .append("bk.author AS book_author, ")
                .append("bk.isbn AS book_isbn, ")
                .append("bk.published_date AS book_published_date, ")
                .append("bk.available_copies AS book_available_copies, ")
                .append("b.borrowed_date, ")
                .append("b.due_date ")
                .append("FROM ")
                .append("borrow b ")
                .append("JOIN member m ON b.member_id = m.id ")
                .append("JOIN book bk ON b.book_id = bk.id ")
                .append("WHERE b.id = :id");

        return tranSqlServices.getMap(sql.toString(),  new MapSqlParameterSource(param));
    }

    @Override
    public List<Map<String, Object>> getAllBorrows() {
        StringBuilder sql = new StringBuilder();
        sql.append("select ")
                .append("b.id AS borrow_id, ")
                .append("b.member_id as member_id, ")
                .append("m.name AS member_name, ")
                .append("m.phone AS member_phone, ")
                .append("bk.id AS book_id, ")
                .append("bk.title AS book_title, ")
                .append("bk.author AS book_author, ")
                .append("bk.isbn AS book_isbn, ")
                .append("bk.published_date AS book_published_date, ")
                .append("bk.available_copies AS book_available_copies, ")
                .append("b.borrowed_date, ")
                .append("b.due_date ")
                .append("FROM ")
                .append("borrow b ")
                .append("JOIN member m ON b.member_id = m.id ")
                .append("JOIN book bk ON b.book_id = bk.id ");

        return tranSqlServices.getList(sql.toString());
    }

    @Override
    public Integer lendBook(Borrow borrow) {
        Map<String, Object> bookDetails = bookRepo.getBookById(borrow.getBookId());
        int availableCopies = (int) bookDetails.get("available_copies");

        if (availableCopies <= 0) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BOOK, borrow.getBookId())
                    ));
        }

        int updatedCopies = availableCopies - 1;
        bookRepo.updateBook(Book.builder().id(borrow.getBookId()).availableCopies(updatedCopies).build());

        return createBorrow(borrow);
    }

    @Override
    public Integer acceptCopies(Borrow borrow) {
        Map<String, Object> bookDetails = bookRepo.getBookById(borrow.getBookId());
        int availableCopies = (int) bookDetails.get("available_copies");

        int updatedCopies = availableCopies + 1;
        bookRepo.updateBook(Book.builder().id(borrow.getBookId()).availableCopies(updatedCopies).build());

        borrow.setDueDate(LocalDate.now());
        return updateBorrow(borrow);
    }

    private boolean isMemberExist(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);
        String sQuery = "select count(id) from member where id=:id";

        int i = tranSqlServices.getInteger(sQuery, new MapSqlParameterSource(param));

        if(i > 0) {
            return true;
        }else {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(MEMBER, id)
                    ));
        }
    }

    private boolean isBookExist(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);
        String sQuery = "select count(id) from book where id=:id";

        int i = tranSqlServices.getInteger(sQuery, new MapSqlParameterSource(param));

        if(i > 0) {
            return true;
        }else {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BOOK, id)
                    ));
        }
    }
}
