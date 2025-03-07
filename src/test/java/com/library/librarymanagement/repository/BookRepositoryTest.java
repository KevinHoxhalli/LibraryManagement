package com.library.librarymanagement.repository;

import com.library.librarymanagement.enity.Book;
import org.apache.commons.lang3.RandomStringUtils;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;


    @Test
    void saveAndCheckBookIsbn() {


    }
}
