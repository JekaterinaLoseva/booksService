package com.company.books.service;

import com.company.books.model.Book;
import com.company.books.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    static final Long ID = 1L;
    public static final String KEYWORD = "some";

    @MockBean
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository);
    }

    @Test
    public void testShouldReturnAllBook() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        given(bookRepository.findAll()).willReturn(books);
        List<Book> expected = bookService.findAllBooks();
        Assertions.assertEquals(expected, books);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testShouldFindByWord() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        given(bookRepository.search(KEYWORD)).willReturn(books);
        List<Book> expected = bookService.searchBooks(KEYWORD);
        Assertions.assertEquals(expected, books);
        verify(bookRepository).search(KEYWORD);
    }

    @Test
    public void testShouldUpdateBook() {
        final Optional<Book> expected = Optional.empty();
        given(bookRepository.findById(1L)).willReturn(expected);
        final Optional<Book> actual = bookService.editBook(1L);
        assertThat(actual).isEqualTo(expected);
        then(bookRepository).should().findById(1L);
    }

    @Test
    public void testShouldFindBookById() {
        final Book book = new Book(1L,"Clean Code", "Robert C. Martin", "link",
                "java", "2002", true);
        final Optional<Book> optionalBook = Optional.ofNullable(book);
        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);

        final Book actualBook = bookService.findBookById(ID);

        Assertions.assertNotNull(actualBook, "Null book returned");
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).findAll();
    }

    @Test
    public void testShouldNotFindBookById() {
        final Optional<Book> optionalBook = Optional.empty();
        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);

        final Book actualBook = bookService.findBookById(ID);

        Assertions.assertNull(actualBook, "Null book returned");
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).findAll();
    }

    @Test
    public void testShouldSaveBook() {
        final Book expectedBook = new Book();
        given(bookRepository.save(expectedBook)).willAnswer(invocation -> {
            final Book toSave = invocation.getArgument(0);
            toSave.setId(ID);
            return toSave;
        });

        final Book actual = bookService.save(expectedBook);

        assertThat(actual).isEqualTo(expectedBook);
        then(bookRepository).should().save(expectedBook);
        then(bookRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void testShouldDeleteBook(){
        willDoNothing().given(bookRepository).deleteById(1L);
        bookService.delete(1L);
        then(bookRepository).should().deleteById(1L);
    }
}
