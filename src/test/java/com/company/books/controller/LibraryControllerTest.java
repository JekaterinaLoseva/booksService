package com.company.books.controller;

import com.company.books.model.Book;
import com.company.books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
class LibraryControllerTest {

    public static final Long ID = 1L;
    @Mock
    BookService bookService;

    LibraryController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        openMocks(this);

        controller = new LibraryController(bookService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testListOfBooks() throws Exception {
        Book book = new Book();
        List<Book> allBooks = Arrays.asList(book);

        when(bookService.findAllBooks()).thenReturn(allBooks);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books-list"))
                .andExpect(model().attributeExists("books"));
        then(bookService).should().findAllBooks();
    }

    @Test
    void testFindBookById() throws Exception {
        Book book = new Book();
        book.setId(ID);
        when(bookService.findBookById(ID)).thenReturn(book);

        mockMvc.perform(get("/book/" + ID))
                .andExpect(status().isOk())
                .andExpect(view().name("list-book"))
                .andExpect(model().attributeExists("book"));
        then(bookService).should().findBookById(ID);
    }

    @Test
    void testAddBook() throws Exception {
        Book book = new Book();
        book.setId(ID);
        book.setTitle("title");
        book.setAuthor("author");
        book.setLink("link");
        book.setCategory("category");
        book.setYear("2009");
        book.setIsAvailable(true);
        when(bookService.findBookById(ID)).thenReturn(book);

        mockMvc.perform(get("/add-book/"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-book"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void testSaveBook() throws Exception {
        Book book = new Book();
        when(bookService.save(book)).thenReturn(book);

        mockMvc.perform(post("/save/")
                .flashAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("books"));
        then(bookService).should().save(book);
    }

    @Test
    void testEditBook() throws Exception {
        Book book = new Book();
        when(bookService.findBookById(ID)).thenReturn(book);

        mockMvc.perform(get("/edit/")
                .param("id", ID.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("id", ID));
    }

    @Test
    void testDeleteBook() throws Exception {
        given(bookService.editBook(ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/delete/")
                .param("id", ID.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", ID))
                .andExpect(view().name("delete"));
        then(bookService).should().editBook(ID);
    }

    @Test
    void testDeleteSaveBook() throws Exception {
        willDoNothing().given(bookService).delete(ID);

        mockMvc.perform(post("/delete/")
                .param("id", ID.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("books"));
        then(bookService).should().delete(ID);
    }
}


