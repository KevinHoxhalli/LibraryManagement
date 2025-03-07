package com.library.librarymanagement.api.controller;

import com.library.librarymanagement.api.model.request.StoreRequest;
import com.library.librarymanagement.api.model.response.StoreResponse;
import com.library.librarymanagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    private MockMvc mockMvc;
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }


    @Test
    void storeBookWithValidRequestSuccess() throws Exception {
        StoreRequest storeRequest = new StoreRequest();
        storeRequest.setIsbn("1234567890123");
        storeRequest.setCategories(Set.of("Science", "Fantasy"));

        StoreResponse storeResponse = StoreResponse.builder().success(true).message("OK").build();

        when(bookService.storeBook(any(StoreRequest.class))).thenReturn(storeResponse);

        mockMvc.perform(post("/api/books/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"));
    }


}
