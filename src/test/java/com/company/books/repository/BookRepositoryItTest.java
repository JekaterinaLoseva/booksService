package com.company.books.repository;

import com.company.books.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryItTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCrud() {
        Book book1 = new Book(1L,"Clean Code", "Robert C. Martin", "link", "java", "2002", true);
        Book book2 = new Book(2L, "Code Complete", "Steve McConnell",
                "link", "java", "2005", true);
        Book book3 = new Book(3L, "Refactoring", "Martin Fowler",
                "link", "java", "2007", true);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        Iterable<Book> books = bookRepository.findAll();
        assertEquals(3, bookRepository.findAll().size());
        Assertions.assertThat(books).extracting(Book::getTitle).containsOnly("Clean Code", "Code Complete", "Refactoring");

        bookRepository.deleteAll();
        Assertions.assertThat(bookRepository.findAll()).isEmpty();
    }
}
