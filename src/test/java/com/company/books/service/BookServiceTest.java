package com.company.books.service;

import com.company.books.model.Book;
import com.company.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    static final Long ID = 1L;
    private Book book;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        book = new Book(1L,"Clean Code", "Robert C. Martin", "link",
                "java", "2002", true);

    }

    @Test
    void testShouldReturnAllBooks() {
        Book book2 = new Book(2L,"Code Complete","Steve McConnell",
                "link", "education", "2005", true);
        given(bookRepository.findAll()).willReturn(Arrays.asList(book, book2));
        List<Book> books = bookService.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void testShouldReturnEmptyBooksList() {
        given(bookRepository.findAll()).willReturn(Collections.emptyList());
        List<Book> books = bookService.findAllBooks();
        assertThat(books).isEmpty();
        assertThat(books.size()).isZero();
    }

    @Test
    void testShouldFindByWord() {
        when(bookRepository.search("java")).thenReturn(Collections.singletonList(book));
        List<Book> searchedBook = bookService.searchBooks(book.getCategory());
        verify(bookRepository).search("java");
        assertThat(searchedBook.size()).isEqualTo(1);
    }

    @Test
    void testShouldUpdateBook() {
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        book.setYear("2013");
        Optional<Book> updatedBook = bookService.editBook(1L);
        assertThat(updatedBook.get().getYear()).isEqualTo("2013");
    }

    @Test
    void testShouldFindBookById() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        Book actualBook = bookService.findBookById(book.getId()).get();
        assertThat(actualBook).isNotNull();
    }

    @Test
    void testShouldSaveBook() {
        given(bookRepository.save(book)).willReturn(book);
        Book savedBook = bookService.save(book);

        assertThat(savedBook).isNotNull();
        then(bookRepository).should().save(savedBook);
    }

    @Test
    void testShouldDeleteBook(){
        willDoNothing().given(bookRepository).deleteById(ID);
        bookService.delete(ID);

        verify(bookRepository, times(1)).deleteById(ID);
    }
}
