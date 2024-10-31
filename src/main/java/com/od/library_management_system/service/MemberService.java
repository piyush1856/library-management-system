package com.od.library_management_system.service;

import com.od.library_management_system.entity.Member;
import com.od.library_management_system.utils.Response;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<Response> createMember(Member member);

    ResponseEntity<Response> getMemberById(Long id);

    ResponseEntity<Response> updateMember(Member member);

    ResponseEntity<Response> deleteMemberById(Long id);
    ResponseEntity<Response> getAllMembers();


}
