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

    private static final Long ID = 1L;
    private static final Long ID2 = 2L;
    private static final Long ID3 = 3L;
    private static final Long ID4 = 4L;
    private static final Long ID5 = 5L;
    private static final Long ID6 = 6L;
    private static final int SIZE = 5;

    @Autowired
    private BookRepository bookRepository;

    // test checks the size of the returned book list
    @Test
    void testFindAllBooks() {
        List<Book> actual = bookRepository.findAll();
        List<Book> expected = generateValidBooks();
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
        assertEquals(SIZE, actual.size());
    }

    // test is checking if the method returns the expected book by id
    @Test
    void testFindBookById() {
        Optional<Book> actual = bookRepository.findById(ID);
        Book book = new Book(ID, "Clean Code", "Robert C. Martin",
                "Even bad code can function. But if code is not clean, "
                        + "it can bring a development organization to its knees.",
                "https://www.oreilly.com/library/view/clean-code-a/9780136083238/",
                "education", "2008", true);
        Optional<Book> expected = Optional.of(book);
        assertThat(expected).isEqualTo(actual);
    }

    // test is checking if the method returns not the expected book by id
    @Test
    void testFindBookByIdInvalid() {
        final Optional<Book> actual = bookRepository.findById(ID2);
        Book book = new Book(ID, "Clean Code", "Robert C. Martin", "description",
                "https://www.oreilly.com/library/view/clean-code-a/9780136083238/",
                "education", "2008", true);
        Optional<Book> expected = Optional.of(book);
        assertThat(expected).isNotEqualTo(actual);
    }

    // test is checking if the method returns a list of books with the keyword
    @Test
    void testFindBookByWord() {
        String keyword = "Code";
        final List<Book> actual = bookRepository.search(keyword);
        assertEquals(2, actual.size());
    }

    // test is checking if the method updates a book correctly
    @Test
    void testUpdateBook() {
        Optional<Book> actual = bookRepository.findById(ID3);
        Book book = new Book(ID3, "Refactoring", "Martin Fowler", "description",
                "https://www.oreilly.com/library/view/refactoring-improving-the/9780134757681/",
                "education", "2018", true);
        book.setTitle("Clean Codes");
        Optional<Book> expected = Optional.of(book);
        bookRepository.save(book);
        assertThat(expected).isEqualTo(actual);
    }

    // test is checking if the method correctly adds a book to the repository
    @Test
    void testAddBook() {
        Book book = new Book();
        book.setId(ID6);
        book.setTitle("Clean Codes");
        book.setAuthor("author");
        book.setDescription("description");
        book.setLink("link");
        book.setCategory("category");
        book.setYear("2008");
        book.setIsAvailable(false);
        Book expected = new Book(ID6, "Clean Codes", "author", "description",
                "link", "category", "2008", false);
        Book actual = bookRepository.save(book);
        assertThat(expected).isEqualTo(actual);
    }

    // test is checking if the method correctly deletes a book from the repository
    @Test
    void testDeleteBook() {
        Optional<Book> actual = bookRepository.findById(ID2);
        Book book = new Book(ID2, "Code Complete", "Steve McConnell", "description",
                "https://www.oreilly.com/library/view/code-complete-2nd/0735619670/",
                "education", "2005", true);
        bookRepository.delete(book);
        Optional<Book> expected = Optional.of(book);
        assertThat(expected).isEqualTo(actual);
    }

    // helper method to generate a list of valid books
    private List<Book> generateValidBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(ID, "Clean Code", "Robert C. Martin",
                "Even bad code can function. But if code is not clean, "
                        + "it can bring a development organization to its knees.",
                "https://www.oreilly.com/library/view/clean-code-a/9780136083238/",
                "education", "2008", true));
        books.add(new Book(ID2, "Code Complete", "Steve McConnell",
                "Widely considered one of the best practical guides to programming, Steve McConnell's "
                        + "original CODE COMPLETE has been helping developers write better software for more than a decade.",
                "https://www.oreilly.com/library/view/code-complete-2nd/0735619670/",
                "education", "2005", true));
        books.add(new Book(ID3, "Refactoring", "Martin Fowler",
                "Fully Revised and Updated Includes New Refactorings and Code Examples",
                "https://www.oreilly.com/library/view/refactoring-improving-the/9780134757681/",
                "education", "2018", true));
        books.add(new Book(ID4, "Design Patterns", "Eric Freeman",
                "With Design Patterns, you get to take advantage of the best practices and experience of "
                        + "others so you can spend your time on something more challenging",
                "https://www.oreilly.com/library/view/head-first-design/9781492077992/",
                "education", "2020", false));
        books.add(new Book(ID5, "Grokking Algorithms", "Aditya Bhargava",
                "Grokking Algorithms is a friendly take on this core computer science topic",
                "https://www.oreilly.com/library/view/grokking-algorithms/9781617292231/",
                "education", "2016", true));
        return books;
    }
}
