package com.library.librarymanagement.api.controller;

import com.library.librarymanagement.api.model.request.SearchRequest;
import com.library.librarymanagement.api.model.request.StoreRequest;
import com.library.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/store")
    public ResponseEntity<?> storeBook(@Valid @RequestBody StoreRequest storeRequest) {
        return ResponseEntity.ok().body(bookService.storeBook(storeRequest));
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchBooks(@Valid @RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok().body(bookService.searchBooksByTags(searchRequest));
    }


//    @GetMapping("/search")
//    public ResponseEntity<?> searchBooks(@RequestParam Set<String> categories) {
//        return ResponseEntity.ok().body(bookService.searchBooksByTags(categories));
//    }
}
