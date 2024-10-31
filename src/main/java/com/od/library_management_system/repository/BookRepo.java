package com.od.library_management_system.repository;

import com.od.library_management_system.entity.Book;
import com.od.library_management_system.entity.Member;

import java.util.List;
import java.util.Map;

public interface BookRepo {

    Integer createBook(Book book);

    boolean isBookExist(Long id);

    Map<String, Object> getBookById(Long id);

    Integer deleteBookById(Long id);

    Integer updateBook(Book book);

    List<Map<String, Object>> getAllBooks();

    List<Map<String, Object>> searchBook(String title, String author, String isbn);
}
