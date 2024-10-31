package com.od.library_management_system.service;

import com.od.library_management_system.entity.Borrow;
import com.od.library_management_system.enums.ResponseCode;
import com.od.library_management_system.repository.BorrowRepo;
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
import static com.od.library_management_system.utils.Constants.BORROW;

@Service
public class BorrowServiceImpl implements BorrowService{

    @Autowired
    private BorrowRepo borrowRepo;

    @Transactional
    @Override
    public ResponseEntity<Response> createBorrow(Borrow borrow) {
        Integer rowAffected = borrowRepo.createBorrow(borrow);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Borrow Created Successfully",
                    HttpStatus.CREATED, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BORROW, borrow.getId())
            ));
        }
    }

    @Override
    public ResponseEntity<Response> getBorrowById(Long id) {
        return ResponseUtility.buildSuccessResponseEntity(borrowRepo.getBorrowById(id),
                HttpStatus.OK, 1, 1, null);
    }

    @Transactional
    @Override
    public ResponseEntity<Response> updateBorrow(Borrow borrow) {
        Integer rowAffected = borrowRepo.updateBorrow(borrow);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Borrow Updated Successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BORROW, borrow.getId())
            ));
        }
    }

    @Override
    public ResponseEntity<Response> deleteBorrowById(Long id) {
        Integer rowAffected = borrowRepo.deleteBorrowById(id);

        if(rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Borrow deleted successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new NoSuchElementException(ResponseCode.ERR_LIB_0004.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BORROW, id)
            ));
        }
    }

    @Override
    public ResponseEntity<Response> getAllBorrows() {
        List<Map<String, Object>> allBorrows = borrowRepo.getAllBorrows();
        return  ResponseUtility.buildSuccessResponseEntity(allBorrows,
                HttpStatus.OK, allBorrows.size(), allBorrows.size(), null);
    }

    @Transactional
    @Override
    public ResponseEntity<Response> lendBook(Borrow borrow) {
        Integer rowAffected = borrowRepo.lendBook(borrow);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Book Borrowed Successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BORROW, borrow.getId())
            ));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Response> acceptCopies(Borrow borrow) {
        Integer rowAffected = borrowRepo.acceptCopies(borrow);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Book Accepted Successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(BORROW, borrow.getId())
            ));
        }
    }
}
