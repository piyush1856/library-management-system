package com.od.library_management_system.repository;

import com.od.library_management_system.entity.Borrow;

import java.util.List;
import java.util.Map;

public interface BorrowRepo {
    Integer createBorrow(Borrow borrow);

    boolean isBorrowExist(Long id);

    Integer updateBorrow(Borrow borrow);

    Integer deleteBorrowById(Long id);

    Map<String, Object> getBorrowById(Long id);

    List<Map<String, Object>> getAllBorrows();

    Integer lendBook(Borrow borrow);

    Integer acceptCopies(Borrow borrow);

}
