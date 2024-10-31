package com.od.library_management_system.controller;

import com.od.library_management_system.entity.Book;
import com.od.library_management_system.entity.Member;
import com.od.library_management_system.service.BookService;
import com.od.library_management_system.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity<Response> createBook(@Valid @RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping()
    public ResponseEntity<Response> updateBookById(@Valid @RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBookById(@PathVariable("id") Long id) {
        return bookService.deleteBookById(id);
    }

    @GetMapping()
    public ResponseEntity<Response> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn
    ) {
        return bookService.searchBooks(title, author, isbn);
    }
}
