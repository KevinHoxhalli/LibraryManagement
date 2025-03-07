package com.library.librarymanagement.service;

import com.library.librarymanagement.api.model.request.SearchRequest;
import com.library.librarymanagement.api.model.request.StoreRequest;
import com.library.librarymanagement.api.model.response.SearchResponse;
import com.library.librarymanagement.api.model.response.StoreResponse;
import com.library.librarymanagement.enity.Book;
import com.library.librarymanagement.enity.Category;
import com.library.librarymanagement.exception.BadRequestException;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.library.librarymanagement.enums.ResponseMessageEnum.*;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public StoreResponse storeBook(StoreRequest storeRequest) {
        try {
            String isbn = storeRequest.getIsbn();

            Set<String> categories = storeRequest.getCategories();

            Book book = bookRepository.findById(isbn).orElse(new Book(isbn));

            book.getCategories().clear();

            for (String categoryName : categories) {
                Category category = categoryRepository.findByName(categoryName)
                        .orElseThrow(() -> new BadRequestException(CATEGORY_NOT_FOUND.toValue()));
//                        .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
                book.getCategories().add(category);
            }

            bookRepository.save(book);

            return StoreResponse.builder().message(STORE_SUCCESS.toValue()).build();

        } catch (BadRequestException e) {
            return StoreResponse.builder().success(false).message(e.getMessage()).build();
        } catch (Exception e) {
            return StoreResponse.builder().success(false).message(SERVER_PROBLEM.toValue()).build();
        }

    }

    public SearchResponse searchBooksByTags(SearchRequest searchRequest) {
        try {
            Set<String> categories = searchRequest.getCategories();

            Set<String> isbns = bookRepository.findIsbnsByCategories(categories, categories.size());

            return SearchResponse.builder().total(isbns.size()).data(isbns).build();
        } catch (Exception e) {
            return SearchResponse.builder().success(false).message(SERVER_PROBLEM.toValue()).build();
        }
    }

}
