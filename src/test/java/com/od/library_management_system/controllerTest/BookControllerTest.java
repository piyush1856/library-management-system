package com.od.library_management_system.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.od.library_management_system.controller.BookController;
import com.od.library_management_system.entity.Book;
import com.od.library_management_system.service.BookService;
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

@WebMvcTest(value = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("123456789")
                .publishedDate(LocalDate.now())
                .availableCopies(5)
                .build();
    }

    @Test
    public void testCreateBook() throws Exception {
        String requestString = objectMapper.writeValueAsString(book);


        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.CREATED);
        Mockito.when(bookService.createBook(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(bookService, Mockito.times(1)).createBook(any());
    }

    @Test
    public void testGetBookById() throws Exception {
        Mockito.when(bookService.getBookById(anyLong())).thenReturn(new ResponseEntity<>(new Response(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/book/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(bookService, Mockito.times(1)).getBookById(1L);
    }

    @Test
    public void testUpdateBookById() throws Exception {
        String requestString = objectMapper.writeValueAsString(book);

        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(bookService.updateBook(any())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/book")
                        .content(requestString)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(bookService, Mockito.times(1)).updateBook(any());
    }

    @Test
    public void testDeleteBookById() throws Exception {
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(new Response(), HttpStatus.OK);
        Mockito.when(bookService.deleteBookById(anyLong())).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(bookService, Mockito.times(1)).deleteBookById(1L);
    }


    @Test
    public void testGetAllBooks() throws Exception {
        Mockito.when(bookService.getAllBooks()).thenReturn(new ResponseEntity<>(new Response(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(bookService, Mockito.times(1)).getAllBooks();
    }

    @Test
    public void testSearchBooks() throws Exception {
        Mockito.when(bookService.searchBooks(any(), any(), any())).thenReturn(new ResponseEntity<>(new Response(), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/book/search")
                        .param("title", "Test")
                        .param("author", "Test Author")
                        .param("isbn", "123456789")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(bookService, Mockito.times(1)).searchBooks("Test", "Test Author", "123456789");
    }

}
