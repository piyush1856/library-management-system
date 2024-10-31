package com.od.library_management_system.service;

import com.od.library_management_system.entity.Book;
import com.od.library_management_system.entity.Member;
import com.od.library_management_system.utils.Response;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<Response> createBook(Book book);

    ResponseEntity<Response> getBookById(Long id);

    ResponseEntity<Response> updateBook(Book book);

    ResponseEntity<Response> deleteBookById(Long id);

    ResponseEntity<Response> getAllBooks();

    ResponseEntity<Response> searchBooks(String title, String author, String isbn);

}
