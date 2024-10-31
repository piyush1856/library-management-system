package com.od.library_management_system.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.od.library_management_system.controller.MemberController;
import com.od.library_management_system.entity.Member;
import com.od.library_management_system.service.MemberService;
import com.od.library_management_system.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(value = MemberController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private Member member;

    @BeforeEach
    public void setup() {
        member = Member.builder()
                .id(1L)
                .name("Test Member")
                .phone("1234567890")
                .registeredDate(LocalDate.now())
                .build();
    }

    @Test
    public void testCreateMember() throws Exception {
        String requestString = objectMapper.writeValueAsString(member);

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.CREATED);
        Mockito.when(memberService.createMember(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/member")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(memberService, Mockito.times(1)).createMember(any());
    }

    @Test
    public void testGetMemberById() throws Exception {
        Mockito.when(memberService.getMemberById(anyLong())).thenReturn(new ResponseEntity<>(new Response(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/member/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(memberService, Mockito.times(1)).getMemberById(1L);
    }

    @Test
    public void testUpdateMemberById() throws Exception {
        String requestString = objectMapper.writeValueAsString(member);

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(memberService.updateMember(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/member")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(memberService, Mockito.times(1)).updateMember(any());
    }

    @Test
    public void testDeleteMemberById() throws Exception {
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(memberService.deleteMemberById(anyLong())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/member/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(memberService, Mockito.times(1)).deleteMemberById(1L);
    }

    @Test
    public void testGetAllMembers() throws Exception {
        Mockito.when(memberService.getAllMembers()).thenReturn(new ResponseEntity<>(new Response(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/member")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(memberService, Mockito.times(1)).getAllMembers();
    }


}

