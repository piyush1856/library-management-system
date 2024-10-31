package com.od.library_management_system.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.od.library_management_system.controller.BorrowController;
import com.od.library_management_system.entity.Borrow;
import com.od.library_management_system.service.BorrowService;
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

@WebMvcTest(value = BorrowController.class)
public class BorrowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowService borrowService;

    @Autowired
    private ObjectMapper objectMapper;

    private Borrow borrow;

    @BeforeEach
    public void setup() {
        borrow = Borrow.builder()
                .id(1L)
                .memberId(1L)
                .bookId(1L)
                .borrowedDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .build();
    }

    @Test
    public void testCreateBorrow() throws Exception {
        String requestString = objectMapper.writeValueAsString(borrow);

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.CREATED);
        Mockito.when(borrowService.createBorrow(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/borrow")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(borrowService, Mockito.times(1)).createBorrow(any());
    }

    @Test
    public void testLendBook() throws Exception {
        String requestString = objectMapper.writeValueAsString(borrow);

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(borrowService.lendBook(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/borrow/lend")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(borrowService, Mockito.times(1)).lendBook(any());
    }

    @Test
    public void testAcceptBook() throws Exception {
        String requestString = objectMapper.writeValueAsString(borrow);

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(borrowService.acceptCopies(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/borrow/accept")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(borrowService, Mockito.times(1)).acceptCopies(any());
    }

    @Test
    public void testGetBorrowById() throws Exception {
        Mockito.when(borrowService.getBorrowById(anyLong())).thenReturn(new ResponseEntity<>(new Response(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/borrow/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(borrowService, Mockito.times(1)).getBorrowById(1L);
    }

    @Test
    public void testUpdateBorrowById() throws Exception {
        String requestString = objectMapper.writeValueAsString(borrow);

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(borrowService.updateBorrow(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/borrow")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(borrowService, Mockito.times(1)).updateBorrow(any());
    }

    @Test
    public void testDeleteBorrowById() throws Exception {
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(borrowService.deleteBorrowById(anyLong())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/borrow/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(borrowService, Mockito.times(1)).deleteBorrowById(1L);
    }

    @Test
    public void testGetAllBorrows() throws Exception {
        Mockito.when(borrowService.getAllBorrows()).thenReturn(new ResponseEntity<>(new Response(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/borrow")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(borrowService, Mockito.times(1)).getAllBorrows();
    }

}

