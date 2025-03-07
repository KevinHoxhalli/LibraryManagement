package com.library.librarymanagement.service;

import com.library.librarymanagement.api.model.request.SearchRequest;
import com.library.librarymanagement.api.model.request.StoreRequest;
import com.library.librarymanagement.api.model.response.SearchResponse;
import com.library.librarymanagement.api.model.response.StoreResponse;
import com.library.librarymanagement.enity.Book;
import com.library.librarymanagement.enity.Category;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static com.library.librarymanagement.enums.ResponseMessageEnum.CATEGORY_NOT_FOUND;
import static com.library.librarymanagement.enums.ResponseMessageEnum.SERVER_PROBLEM;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class StoreBookTests {
        @Test
        void storeNewBook() {
            // create req
            StoreRequest storeRequest = new StoreRequest();
            storeRequest.setIsbn("1234567654321");
            storeRequest.setCategories(Set.of("Fiction", "Science"));
            // mock methods
            Book book = new Book("1234567654321");
            book.getCategories().add(new Category("Fiction"));
            book.getCategories().add(new Category("Science"));

            when(bookRepository.findById("1234567654321")).thenReturn(java.util.Optional.of(book));
            when(categoryRepository.findByName("Fiction")).thenReturn(java.util.Optional.of(new Category("Fiction")));
            when(categoryRepository.findByName("Science")).thenReturn(java.util.Optional.of(new Category("Science")));

            StoreResponse response = bookService.storeBook(storeRequest);
            assertTrue(response.isSuccess());
            assertEquals("OK", response.getMessage());
        }

        @Test
        void storeUpdateBook() {
            // create req
            StoreRequest storeRequest = new StoreRequest();
            storeRequest.setIsbn("1234567654321");
            storeRequest.setCategories(Set.of("Fiction", "Science"));
            // mock methods
            Book book = new Book("1234567654321");
            book.getCategories().add(new Category("Fiction"));
            book.getCategories().add(new Category("Science"));

            when(bookRepository.findById("1234567654321")).thenReturn(java.util.Optional.of(book));
            when(categoryRepository.findByName("Fiction")).thenReturn(java.util.Optional.of(new Category("Fiction")));
            when(categoryRepository.findByName("Science")).thenReturn(java.util.Optional.of(new Category("Science")));

            StoreResponse response = bookService.storeBook(storeRequest);
            assertEquals(storeRequest.getCategories().size(), book.getCategories().size());
            assertTrue(response.isSuccess());
            assertEquals("OK", response.getMessage());
        }

        @Test
        void storeFailsWhenCategoryNotFound() {
            // create req
            StoreRequest storeRequest = new StoreRequest();
            storeRequest.setIsbn("1234567654321");
            storeRequest.setCategories(Set.of("Test", "Science"));
            // mock methods
            Book book = new Book("1234567654321");
            book.getCategories().add(new Category("Test"));
            book.getCategories().add(new Category("Science"));

            when(bookRepository.findById("1234567654321")).thenReturn(java.util.Optional.of(book));
            when(categoryRepository.findByName("Test")).thenReturn(java.util.Optional.empty());

            StoreResponse response = bookService.storeBook(storeRequest);
            assertFalse(response.isSuccess());
            assertEquals(CATEGORY_NOT_FOUND.toValue(), response.getMessage());
        }
    }

    @Nested
    class SearchBookTests {
        @Test
        void whenSearchBooksByTags_thenReturnMatchingBooks() {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setCategories(Set.of("Fiction", "Science"));

            when(bookRepository.findIsbnsByCategories(Set.of("Fiction", "Science"), 2))
                    .thenReturn(Set.of("1234567654321", "1234567654322"));

            SearchResponse response = bookService.searchBooksByTags(searchRequest);

            assertTrue(response.isSuccess());
            assertEquals(2, response.getTotal());
            assertEquals(Set.of("1234567654321", "1234567654322"), response.getData());
        }

        @Test
        void whenSearchBooksByTagsWithException_thenReturnServerProblem() {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setCategories(Set.of("Fiction", "Science"));

            when(bookRepository.findIsbnsByCategories(Set.of("Fiction", "Science"), 2))
                    .thenThrow(new RuntimeException("Exception"));

            SearchResponse response = bookService.searchBooksByTags(searchRequest);

            assertFalse(response.isSuccess());
            assertEquals(SERVER_PROBLEM.toValue(), response.getMessage());
        }
    }

}
