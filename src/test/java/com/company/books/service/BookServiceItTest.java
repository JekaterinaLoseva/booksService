package com.company.books.service;

import com.company.books.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookServiceItTest {

    @Autowired
    BookService service;

    @Test
    public void testBookService() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setLink("link");
        book.setCategory("category");
        book.setYear("2005");
        book.setIsAvailable(true);

        Book actualBook = service.save(book);

        assertNotNull(actualBook);
        assertNotNull(actualBook.getId());
        assertEquals(book.getTitle(), actualBook.getTitle());
        assertEquals(book.getAuthor(), actualBook.getAuthor());
        assertEquals(book.getLink(), actualBook.getLink());
        assertEquals(book.getCategory(), actualBook.getCategory());
        assertEquals(book.getYear(), actualBook.getYear());
        assertEquals(book.getIsAvailable(), actualBook.getIsAvailable());
    }
}
