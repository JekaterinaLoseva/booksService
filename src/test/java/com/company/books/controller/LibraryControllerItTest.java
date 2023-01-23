package com.company.books.controller;

import com.company.books.model.Book;
import com.company.books.repository.BookRepository;
import com.company.books.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "pass22", roles = {"Admin"})
@ActiveProfiles("test")
public class LibraryControllerItTest {

    private static final Long ID5 = 5L;
    private static final Long ID6 = 6L;
    private static final Long ID99 = 99L;
    private static final int SIZE5 = 5;
    private static final int SIZE6 = 6;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository repository;

    private Book book;

    // test to retrieve all books from the database
    @Test
    public void testFindAllBooks() throws Exception {
        final List<Book> books = bookService.findAllBooks();
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("books-list"))
                .andExpect(model().attribute("books", hasSize(greaterThan(0))));
    }

    // test to retrieve all books for user from the database
    @Test
    public void testFindAllUserBooks() throws Exception {
        final List<Book> books = bookService.findAllBooks();
        mockMvc.perform(get("/userBooks"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("user-books"))
                .andExpect(model().attribute("books", hasSize(greaterThan(0))));
    }

    // test to find specified book by id from the database
    @Test
    public void testFindBookById() throws Exception {
        Optional<Book> foundBook = bookService.findBookById(1L);
        assertThat(foundBook.isPresent()).isTrue();

        mockMvc.perform(get("/book/" + 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", foundBook))
                .andExpect(view().name("list-book"));
    }

    // test to find book by id from the database, when id is not correct
    @Test
    public void testFindBookByIdNotFound() throws Exception {
        Optional<Book> foundBook = bookService.findBookById(ID99);
        assertThat(foundBook.isPresent()).isFalse();

        mockMvc.perform(get("/book/" + ID99))
                .andExpect(status().isOk());
    }

    // test to search books with correct keyword
    @Test
    public void testSearchBookSuccess() throws Exception {
        final String keyword = "Code";
        mockMvc.perform(get("/searchBook").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(view().name("searched-books"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(greaterThan(0))))
                .andExpect(model().attribute("keyword", keyword));
    }

    // test to search books with invalid keyword
    @Test
    public void testSearchBookInvalid() throws Exception {
        final String keyword = "test";
        mockMvc.perform(get("/searchBook").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", hasSize((0))));
    }

    // test to add book to the database
    @Test
    public void testAddBook() throws Exception {
        book = new Book(ID6, "Integration testing", "Steve McConnell", "description",
                "link", "java", "2005", true);

        mockMvc.perform(get("/add-book"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-book"));

        mockMvc.perform(post("/save")
                .param("title", book.getTitle())
                .param("author", book.getAuthor())
                .param("category", book.getCategory())
                .param("description", book.getDescription())
                .param("link", book.getLink())
                .param("category", book.getCategory())
                .param("year", book.getYear())
                .param("status", String.valueOf(book.getIsAvailable())).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:books"));

        final List<Book> books = bookService.findAllBooks();
        assertThat(books.size()).isEqualTo(SIZE6);
        assertThat(books.get(SIZE5).getTitle()).isEqualTo("Integration testing");
    }

    // test to edit the book and save updated info into database
    @Test
    public void testEditBookAndSave() throws Exception {
        book = new Book(ID6, "Code Completes", "Steve McConnell", "description", "link",
                "java", "2005", true);
        mockMvc.perform(get("/edit")
                .param("id", String.valueOf(book.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));

        final String updatedTitle = "Clean Codes";
        mockMvc.perform(post("/save")
                .param("id", String.valueOf(book.getId()))
                .param("title", updatedTitle).with(csrf())
                .param("author", book.getAuthor())
                .param("description", book.getDescription())
                .param("link", book.getLink())
                .param("category", book.getCategory())
                .param("year", book.getYear())
                .param("status", String.valueOf(book.getIsAvailable())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:books"));

        final List<Book> books = bookService.findAllBooks();
        assertThat(books.size()).isEqualTo(SIZE6);
        assertThat(books.get(SIZE5).getTitle()).isEqualTo(updatedTitle);
    }

    // test when book can not be saved due to empty or null values
    @Test
    public void testErrorToSaveBook() throws Exception {
        book = new Book(ID6, "", "Steve McConnell", "description",
                "link", "", null, true);

        mockMvc.perform(post("/save"))
                .andExpect(status().is4xxClientError());
    }

    // test to delete book from the database
    @Test
    public void testDeleteBook() throws Exception {
        book = new Book(ID5, "Code Completes", "Steve McConnell", "description", "link",
                "java", "2005", true);
        Long id = book.getId();
        mockMvc.perform(get("/delete")
                .param("id", String.valueOf(book.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("delete"));

        mockMvc.perform(post("/delete")
                .param("id", String.valueOf(book.getId())).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:books"));

        Optional<Book> deletedBook = bookService.findBookById(id);
        assertFalse(deletedBook.isPresent());
    }

}
