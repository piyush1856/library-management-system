package com.od.library_management_system.repository;


import com.od.library_management_system.entity.Book;
import com.od.library_management_system.utils.ResponseUtility;
import com.od.library_management_system.utils.TranSqlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.od.library_management_system.enums.ResponseCode.ERR_LIB_0002;
import static com.od.library_management_system.utils.Constants.*;

@Repository
public class BookRepoImpl implements BookRepo{

    @Autowired
    TranSqlServices tranSqlServices;

    @Override
    public Integer createBook(Book book) {
        Map<String, Object> param = new HashMap<>();
        param.put(TITLE, book.getTitle());
        param.put(AUTHOR, book.getAuthor());
        param.put(ISBN, book.getIsbn());
        param.put(PUB_DATE, book.getPublishedDate());
        param.put(AVAIL_COPIES, book.getAvailableCopies());


        String sql = "INSERT INTO book (title, author, isbn, published_date, available_copies) VALUES (:title, :author, :isbn, :published_date, :available_copies)";
        return tranSqlServices.persist(sql, new MapSqlParameterSource(param));
    }

    @Override
    public boolean isBookExist(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);
        String sQuery = "select count(id) from book where id=:id";

        return tranSqlServices.getInteger(sQuery, new MapSqlParameterSource(param)) > 0;
    }

    @Override
    public Map<String, Object> getBookById(Long id) {
        if(!isBookExist(id)) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BOOK, id)
                    ));
        }

        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);
        String sql = "select id, title, author, isbn, published_date, available_copies from book where id = :id";
        return tranSqlServices.getMap(sql,  new MapSqlParameterSource(param));
    }

    @Override
    public Integer deleteBookById(Long id) {
        if(!isBookExist(id)) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BOOK, id)
                    ));
        }

        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);

        String sql = "delete from book where id = :id";
        return tranSqlServices.updateDelete(sql, param);
    }

    @Override
    public Integer updateBook(Book book) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, book.getId());

        if(!isBookExist(book.getId())) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(BOOK, book.getId())
                    ));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("update book set ");

        boolean isFirstField = true;

        if (Objects.nonNull(book.getTitle())) {
            param.put(TITLE, book.getTitle());
            sql.append(isFirstField ? "" : ", ").append("title = :title");
            isFirstField = false;
        }

        if (Objects.nonNull(book.getAuthor())) {
            param.put(AUTHOR, book.getAuthor());
            sql.append(isFirstField ? "" : ", ").append("author = :author");
            isFirstField = false;
        }

        if (Objects.nonNull(book.getIsbn())) {
            param.put(ISBN, book.getIsbn());
            sql.append(isFirstField ? "" : ", ").append("isbn = :isbn");
            isFirstField = false;
        }

        if (Objects.nonNull(book.getPublishedDate())) {
            param.put(PUB_DATE, book.getPublishedDate());
            sql.append(isFirstField ? "" : ", ").append("published_date = :published_date");
            isFirstField = false;
        }

        if (Objects.nonNull(book.getAvailableCopies())) {
            param.put(AVAIL_COPIES, book.getAvailableCopies());
            sql.append(isFirstField ? "" : ", ").append("available_copies = :available_copies");
        }

        sql.append(" where id=:id");

        return tranSqlServices.persist(sql.toString(), new MapSqlParameterSource(param));
    }

    @Override
    public List<Map<String, Object>> getAllBooks() {
        String sql = "select id, title, author, isbn, published_date, available_copies from book";
        return tranSqlServices.getList(sql);
    }

    @Override
    public List<Map<String, Object>> searchBook(String title, String author, String isbn) {
        Map<String, Object> param = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select id, title, author, isbn, published_date, available_copies from book where 1=1");

        if(Objects.nonNull(title)) {
            sql.append(" and title like :title");
            param.put("title", "%" + title + "%");
        }

        if (Objects.nonNull(author)) {
            sql.append(" and author LIKE :author");
            param.put("author", "%" + author + "%");
        }

        if (Objects.nonNull(isbn)) {
            sql.append(" and isbn LIKE :isbn");
            param.put("isbn", "%" + isbn + "%");
        }

        return tranSqlServices.getList(sql.toString(),  new MapSqlParameterSource(param));
    }
}
