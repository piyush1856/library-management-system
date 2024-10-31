package com.od.library_management_system.service;

import com.od.library_management_system.entity.Member;
import com.od.library_management_system.enums.ResponseCode;
import com.od.library_management_system.repository.MemberRepo;
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

import static com.od.library_management_system.utils.Constants.MEMBER;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepo memberRepo;

    @Transactional
    @Override
    public ResponseEntity<Response> createMember(Member member) {
        Integer rowAffected = memberRepo.createMember(member);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Member Created Successfully",
                    HttpStatus.CREATED, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(MEMBER, member.getId())
            ));
        }
    }

    @Override
    public ResponseEntity<Response> getMemberById(Long id) {
        return ResponseUtility.buildSuccessResponseEntity(memberRepo.getMemberById(id),
                HttpStatus.OK, 1, 1, null);
    }

    @Transactional
    @Override
    public ResponseEntity<Response> updateMember(Member member) {
        Integer rowAffected = memberRepo.updateMember(member);

        if (rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Member Updated Successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new IllegalArgumentException(ResponseCode.ERR_LIB_0001.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(MEMBER, member.getId())
            ));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Response> deleteMemberById(Long id) {
        Integer rowAffected = memberRepo.deleteMemberById(id);

        if(rowAffected > 0) {
            return ResponseUtility.buildSuccessResponseEntity("Member deleted successfully",
                    HttpStatus.OK, 1, 1, null);
        }else {
            throw new NoSuchElementException(ResponseCode.ERR_LIB_0004.getMessage(
                    ResponseUtility.getParamMapWithTypeAndId(MEMBER, id)
            ));
        }

    }

    @Override
    public ResponseEntity<Response> getAllMembers() {
        List<Map<String, Object>> allMembers = memberRepo.getAllMembers();
        return  ResponseUtility.buildSuccessResponseEntity(allMembers,
                HttpStatus.OK, allMembers.size(), allMembers.size(), null);
    }
}
