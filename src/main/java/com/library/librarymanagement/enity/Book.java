package com.library.librarymanagement.enity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Book {

    @Id
    private String isbn;

    @ManyToMany
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            indexes = {
                    @Index(name = "idx_book_category_id", columnList = "category_id")
            }
    )
    private Set<Category> categories = new HashSet<>();

    public Book() {
    }

    public Book(String isbn) {
        this.isbn = isbn;
    }

}