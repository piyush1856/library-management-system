package com.od.library_management_system.repository;

import com.od.library_management_system.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberRepo {

    Integer createMember(Member member);

    boolean isMemberExist(Long id);

    Map<String, Object> getMemberById(Long id);

    Integer deleteMemberById(Long id);

    Integer updateMember(Member member);

    List<Map<String, Object>> getAllMembers();
}
