package com.library.librarymanagement.repository;

import com.library.librarymanagement.enity.Book;
import com.library.librarymanagement.enity.Category;
import org.apache.commons.lang3.RandomStringUtils;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.Set;


@DataJpaTest
@Testcontainers
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("library_db")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @BeforeAll
    static void init(@Autowired ApplicationContext context) {
        CategoryRepository bean = context.getBean(CategoryRepository.class);
        Category science = new Category("Science");
        bean.save(science);
        Category fiction = new Category("Fiction");
        bean.save(fiction);
        Category history = new Category("History");
        bean.save(history);
        bean.save(new Category("Biography"));


    }

    @BeforeEach
    void setUp() { //cleanup
        bookRepository.deleteAll();

        Book initBook = new Book("1234567890123");
        Category fiction = categoryRepository.findByName("Fiction").get();
        initBook.getCategories().add(fiction);
        bookRepository.save(initBook);

        Book secondBook = new Book("1234567890124");
        Category biography = categoryRepository.findByName("Biography").get();
        initBook.getCategories().add(biography);
        initBook.getCategories().add(fiction);
        bookRepository.save(secondBook);

    }


    @Test
    void saveAndFindBook() {
        Book book = new Book("1234567654321");
        Category category = categoryRepository.findByName("Fiction").get();
        book.getCategories().add(category);

        bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.findByIsbn("1234567654321");

        assertTrue(foundBook.isPresent());
        assertEquals("1234567654321", foundBook.get().getIsbn());
        assertEquals(1, foundBook.get().getCategories().size());
        assertEquals(3, bookRepository.count());
    }


    @Test
    void findBooksByCategories() {

        Set<String> set = Set.of("Fiction");
        Set<String> isbns = bookRepository.findIsbnsByCategories(set, set.size());

        assertEquals(1, isbns.size());
        assertTrue(isbns.contains("1234567890123"));
    }

    @Test
    void notFoundCategories() {

        Set<String> set = Set.of("Fantasy");
        Set<String> isbns = bookRepository.findIsbnsByCategories(set, set.size());

        assertEquals(0, isbns.size());
    }

    @Test
    void notFoundWithExtraCategories() {

        Set<String> set = Set.of("Fiction", "Biography");
        Set<String> isbns = bookRepository.findIsbnsByCategories(set, set.size());

        assertEquals(0, isbns.size());
    }


}
