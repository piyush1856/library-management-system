package com.od.library_management_system.controller;

import com.od.library_management_system.entity.Member;
import com.od.library_management_system.service.MemberService;
import com.od.library_management_system.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Validated
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping()
    public ResponseEntity<Response> createMember(@Valid @RequestBody Member member) {
        return memberService.createMember(member);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getMemberById(@PathVariable("id") Long id) {
        return memberService.getMemberById(id);
    }

    @PutMapping()
    public ResponseEntity<Response> updateMemberById(@Valid @RequestBody Member member) {
        return memberService.updateMember(member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMemberById(@PathVariable("id") Long id) {
        return memberService.deleteMemberById(id);
    }

    @GetMapping()
    public ResponseEntity<Response> getAllMembers() {
        return memberService.getAllMembers();
    }
}
