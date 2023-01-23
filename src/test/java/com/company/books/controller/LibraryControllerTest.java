package com.company.books.controller;

import com.company.books.model.Book;
import com.company.books.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LibraryController.class)
@RunWith(SpringRunner.class)
class LibraryControllerTest {

    private static final Long ID = 1L;
    private Book book;

    @MockBean
    private BookService bookService;

    @InjectMocks
    private LibraryController controller;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        controller = new LibraryController(bookService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        book = new Book(ID, "Clean Code", "Robert C. Martin", "description", "link",
                "java", "2002", true);

        assertThat(this.bookService).isNotNull();
    }

    @Test
    void testMainPage() throws Exception {
        mockMvc.perform(get("/mainPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    // Test get all books using template books
    @Test
    void testListOfBooks() throws Exception {
        Book book2 = new Book(2L, "Code Complete", "Steve McConnell", "description", "link",
                "java", "2005", true);
        final List<Book> allBooks = Arrays.asList(book, book2);

        when(bookService.findAllBooks()).thenReturn(allBooks);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books-list"))
                .andExpect(model().attribute("books", hasItems(book, book2)))
                .andExpect(model().attribute("books", Matchers.hasSize(2)))
                .andDo(print());
        then(bookService).should().findAllBooks();
    }

    // Test get all books using template userBooks
    @Test
    void testListOfUserBooks() throws Exception {
        Book book2 = new Book(2L, "Code Complete", "Steve McConnell", "description", "link",
                "java", "2005", true);
        final List<Book> allBooks = Arrays.asList(book, book2);

        when(bookService.findAllBooks()).thenReturn(allBooks);

        mockMvc.perform(get("/userBooks"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-books"))
                .andExpect(model().attribute("books", hasItems(book, book2)))
                .andExpect(model().attribute("books", Matchers.hasSize(2)))
                .andDo(print());
        then(bookService).should().findAllBooks();
    }

    // Test get a book by id using template book
    @Test
    void testFindBookById() throws Exception {
        when(bookService.findBookById(ID)).thenReturn(Optional.of(book));

        assertThat(book).extracting("id", "title", "author", "link", "category", "year", "isAvailable")
                .containsExactly(ID, "Clean Code", "Robert C. Martin", "link", "java", "2002", true);
        mockMvc.perform(get("/book/" + ID))
                .andExpect(status().isOk())
                .andExpect(view().name("list-book"))
                .andExpect(model().attributeExists("book"))
                .andDo(print());
        then(bookService).should().findBookById(ID);
    }

    // Test not find a book by id using template book
    @Test
    void testNotFindBookById() throws Exception {
        given(bookService.findBookById(ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/book/", ID))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // Test search a book by keyword using template searchBook
    @Test
    void testSearchByWord() throws Exception {
        final List<Book> allBooks = Collections.singletonList(book);
        when(bookService.searchBooks(book.getCategory())).thenReturn(allBooks);

        mockMvc.perform(get("/searchBook")
                .param("keyword", ("java")))
                .andExpect(status().isOk())
                .andExpect(view().name("searched-books"))
                .andExpect(model().attribute("books", Matchers.hasSize(1)))
                .andExpect(model().attributeExists("keyword"))
                .andDo(print());
        then(bookService).should().searchBooks("java");
    }

    // Test add a book using template add-book
    @Test
    void testAddBook() throws Exception {
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("title");
        book2.setAuthor("author");
        book2.setDescription("description");
        book2.setLink("link");
        book2.setCategory("category");
        book2.setYear("2009");
        book2.setIsAvailable(true);
        when(bookService.findBookById(2L)).thenReturn(Optional.of(book2));

        assertThat(book2).extracting("id", "title", "author", "description", "link", "category", "year", "isAvailable")
                .containsExactly(2L, "title", "author", "description", "link", "category", "2009", true);
        mockMvc.perform(get("/add-book/"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-book"))
                .andExpect(model().attributeExists("book"))
                .andDo(print());
    }

    // Test save a book using template save
    @Test
    void testSaveBook() throws Exception {
        Book book2 = new Book(2L, "Code Complete", "Steve McConnell", "description", "link",
                "java", "2005", true);
        when(bookService.save(book2)).thenReturn(book2);

        assertThat(book2).extracting("id", "title", "author", "link", "category", "year", "isAvailable")
                .containsExactly(2L, "Code Complete", "Steve McConnell", "link", "java", "2005", true);
        mockMvc.perform(post("/save/")
                .flashAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("books"))
                .andDo(print());
        then(bookService).should().save(book);
    }

    // Test update a book using template edit
    @Test
    void testUpdateBook() throws Exception {
        when(bookService.editBook(ID)).thenReturn(Optional.of(book));
        book.setCategory("new Category");
        book.setYear("2012");

        assertThat(book).extracting("id", "title", "author", "link", "category", "year", "isAvailable")
                .containsExactly(ID, "Clean Code", "Robert C. Martin", "link", "new Category", "2012", true);
        mockMvc.perform(get("/edit/")
                .param("id", ID.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("id", ID));
    }

    // Test edit a book using template delete
    @Test
    void testEditBook() throws Exception {
        given(bookService.editBook(ID)).willReturn(Optional.of(book));

        mockMvc.perform(get("/delete/")
                .param("id", ID.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", ID))
                .andExpect(view().name("delete"));
        then(bookService).should().editBook(ID);
    }

    // Test delete a book using template delete
    @Test
    void testDeleteBook() throws Exception {
        willDoNothing().given(bookService).delete(ID);

        mockMvc.perform(post("/delete/")
                .param("id", ID.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("books"));
        then(bookService).should().delete(ID);
    }
}


