package com.library.librarymanagement.repository;

import com.library.librarymanagement.enity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, String> {


    @Query("SELECT b.isbn FROM Book b JOIN b.categories c WHERE c.name IN :categories GROUP BY b.isbn HAVING COUNT(DISTINCT c.name) >= :categoryCount")
    Set<String> findIsbnsByCategories(@Param("categories") Set<String> categories, @Param("categoryCount") long categoryCount);

}
