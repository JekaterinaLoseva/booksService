package com.company.books.repository;

import com.company.books.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class BookRepositoryItTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindAllBooks() {
        List<Book> actual = bookRepository.findAll();
        List<Book> expected = generateValidBooks();
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
        assertEquals(5, actual.size());
    }

    @Test
    void testFindBookById() {
        Optional<Book> actual = bookRepository.findById(1L);
        Book book = new Book(1L, "Clean Code", "Robert C. Martin",
                "https://www.oreilly.com/library/view/clean-code-a/9780136083238/","education", "2008", true);
        Optional<Book> expected = Optional.of(book);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testFindBookByIdInvalid() {
        final Optional<Book> actual = bookRepository.findById(2L);
        Book book = new Book(1L, "Clean Code", "Robert C. Martin",
                "https://www.oreilly.com/library/view/clean-code-a/9780136083238/","education", "2008", true);
        Optional<Book> expected = Optional.of(book);
        assertThat(expected).isNotEqualTo(actual);
    }

    @Test
    void testFindBookByWord() {
        String keyword = "Code";
        final List<Book> actual = bookRepository.search(keyword);
        assertEquals(2, actual.size());
    }

    @Test
    void testUpdateBook() {
        Optional<Book> actual = bookRepository.findById(3L);
        Book book = new Book(3L, "Refactoring", "Martin Fowler",
                "https://www.oreilly.com/library/view/refactoring-improving-the/9780134757681/","education", "2018", true);
        book.setTitle("Clean Codes");
        Optional<Book> expected = Optional.of(book);
        bookRepository.save(book);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        book.setId(6L);
        book.setTitle("Clean Codes");
        book.setAuthor("author");
        book.setLink("link");
        book.setCategory("category");
        book.setYear("2008");
        book.setIsAvailable(false);
        Book expected = new Book(6L, "Clean Codes", "author",
                "link", "category", "2008", false);
        Book actual = bookRepository.save(book);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testDeleteBook() {
        Optional<Book> actual = bookRepository.findById(2L);
        Book book = new Book(2L,"Code Complete","Steve McConnell",
                "https://www.oreilly.com/library/view/code-complete-2nd/0735619670/", "education",	"2005", true);
        bookRepository.delete(book);
        Optional<Book> expected = Optional.of(book);
        assertThat(expected).isEqualTo(actual);
    }

    private List<Book> generateValidBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Clean Code", "Robert C. Martin",
                "https://www.oreilly.com/library/view/clean-code-a/9780136083238/","education", "2008", true));
        books.add(new Book(2L, "Code Complete",	"Steve McConnell",
                "https://www.oreilly.com/library/view/code-complete-2nd/0735619670/", "education",	"2005", true));
        books.add(new Book(3L, "Refactoring", "Martin Fowler",
                "https://www.oreilly.com/library/view/refactoring-improving-the/9780134757681/","education", "2018", true));
        books.add(new Book(4L, "Design Patterns", "Eric Freeman",
                "https://www.oreilly.com/library/view/head-first-design/9781492077992/","education", "2020", false));
        books.add(new Book(5L, "Grokking Algorithms", "Aditya Bhargava",
                "https://www.oreilly.com/library/view/grokking-algorithms/9781617292231/","education", "2016", true));
        return books;
    }
}
