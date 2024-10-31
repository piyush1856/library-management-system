package com.od.library_management_system.service;

import com.od.library_management_system.entity.Borrow;
import com.od.library_management_system.utils.Response;
import org.springframework.http.ResponseEntity;

public interface BorrowService {

    ResponseEntity<Response> createBorrow(Borrow borrow);

    ResponseEntity<Response> getBorrowById(Long id);

    ResponseEntity<Response> updateBorrow(Borrow borrow);

    ResponseEntity<Response> deleteBorrowById(Long id);

    ResponseEntity<Response> getAllBorrows();

    ResponseEntity<Response> lendBook(Borrow borrow);

    ResponseEntity<Response> acceptCopies(Borrow borrow);

}
