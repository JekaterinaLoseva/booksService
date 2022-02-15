package com.company.books;

import com.company.books.model.Book;
import com.company.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private String category = "Education";

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner initializeBooks() {

        return args -> {

            final Book book1 = new Book(1L, "Clean Code", "Robert C. Martin",
                    "https://www.oreilly.com/library/view/clean-code-a/9780136083238/", category, "2008", true);
            final Book book2 = new Book(2L, "Code Complete", "Steve McConnell",
                    "https://www.oreilly.com/library/view/code-complete-2nd/0735619670/", category, "2005", true);
            final Book book3 = new Book(3L, "Refactoring", "Martin Fowler",
                    "https://www.oreilly.com/library/view/refactoring-improving-the/9780134757681/", category, "2018", true);

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
        };
    }
}

