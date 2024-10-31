package com.od.library_management_system.service;

import com.od.library_management_system.entity.Book;
import com.od.library_management_system.enums.ResponseCode;
import com.od.library_management_system.repository.BookRepo;
import com.od.library_management_system.utils.Response;
import com.od.library_management_system.utils.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.od.library_management_system.utils.Constants.BOOK;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepo bookRepo;

    @Transactional
    @Override
    public ResponseEntity<Response> createBook(Book book) {
        Integer rowAffected = bookRepo.createBook(book);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Book Created Successfully",
                    HttpStatus.CREATED, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BOOK, book.getId())
            ));
        }
    }

    @Override
    public ResponseEntity<Response> getBookById(Long id) {
        return ResponseUtility.buildSuccessResponseEntity(bookRepo.getBookById(id),
                HttpStatus.OK, 1, 1, null);
    }

    @Transactional
    @Override
    public ResponseEntity<Response> updateBook(Book book) {
        Integer rowAffected = bookRepo.updateBook(book);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Book Updated Successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BOOK, book.getId())
            ));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Response> deleteBookById(Long id) {
        Integer rowAffected = bookRepo.deleteBookById(id);

        if(rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Book deleted successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new NoSuchElementException(ResponseCode.ERR_LIB_0004.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BOOK, id)
            ));
        }
    }

    @Override
    public ResponseEntity<Response> getAllBooks() {
        List<Map<String, Object>> allBooks = bookRepo.getAllBooks();
        return  ResponseUtility.buildSuccessResponseEntity(allBooks,
                HttpStatus.OK, allBooks.size(), allBooks.size(), null);
    }

    @Override
    public ResponseEntity<Response> searchBooks(String title, String author, String isbn) {
        List<Map<String, Object>> searchedBooks = bookRepo.searchBook(title, author, isbn);
        return  ResponseUtility.buildSuccessResponseEntity(searchedBooks,
                HttpStatus.OK, searchedBooks.size(), searchedBooks.size(), null);
    }
}
