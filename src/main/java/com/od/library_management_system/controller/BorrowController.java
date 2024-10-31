package com.od.library_management_system.controller;

import com.od.library_management_system.entity.Borrow;
import com.od.library_management_system.service.BorrowService;
import com.od.library_management_system.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
@Validated
public class BorrowController {
    
    @Autowired
    private BorrowService borrowService;

    @PostMapping()
    public ResponseEntity<Response> createBorrow(@Valid @RequestBody Borrow borrow) {
        return borrowService.createBorrow(borrow);
    }

    @PutMapping("/lend")
    public ResponseEntity<Response> lendBook(@Valid @RequestBody Borrow borrow) {
        return borrowService.lendBook(borrow);
    }

    @PutMapping("/accept")
    public ResponseEntity<Response> acceptBook(@Valid @RequestBody Borrow borrow) {
        return borrowService.acceptCopies(borrow);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBorrowById(@PathVariable("id") Long id) {
        return borrowService.getBorrowById(id);
    }

    @PutMapping()
    public ResponseEntity<Response> updateBorrowById(@Valid @RequestBody Borrow borrow) {
        return borrowService.updateBorrow(borrow);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBorrowById(@PathVariable("id") Long id) {
        return borrowService.deleteBorrowById(id);
    }

    @GetMapping()
    public ResponseEntity<Response> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

}
