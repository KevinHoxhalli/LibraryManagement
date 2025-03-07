package com.library.librarymanagement.config;

import com.library.librarymanagement.api.model.request.StoreRequest;
import com.library.librarymanagement.enity.Category;
import com.library.librarymanagement.repository.CategoryRepository;
import com.library.librarymanagement.service.BookService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Profile("init")
public class DatabaseInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final BookService bookService;

    private final List<String> PREDEFINED_CATEGORIES = List.of(
            "Science", "History", "Fantasy", "Romance", "Horror",
            "Thriller", "Comedy", "Drama", "Action", "Adventure",
            "Mystery", "Biography"
    );

    public DatabaseInitializer(CategoryRepository categoryRepository, BookService bookService) {
        this.categoryRepository = categoryRepository;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count() == 0) PREDEFINED_CATEGORIES.forEach(category -> categoryRepository.save(new Category(category)));

        for (int i = 0; i < 1_000_000; i++) {
            String randomTitle = RandomStringUtils.randomNumeric(13);
            Set<String> randomCategories = getRandomCategories();
            StoreRequest storeRequest = new StoreRequest();
            storeRequest.setIsbn(randomTitle);
            storeRequest.setCategories(randomCategories);
            bookService.storeBook(storeRequest);
        }
    }


    private Set<String> getRandomCategories() {
        Random random = new Random();
        int numberOfCategories = random.nextInt(PREDEFINED_CATEGORIES.size()/2) + 1;
        return random.ints(0, PREDEFINED_CATEGORIES.size())
                .distinct()
                .limit(numberOfCategories)
                .mapToObj(PREDEFINED_CATEGORIES::get)
                .collect(Collectors.toSet());
    }
}
