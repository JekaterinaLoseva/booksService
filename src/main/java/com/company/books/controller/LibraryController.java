package com.company.books.controller;

import com.company.books.model.Book;
import com.company.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;;

import java.util.List;
import java.util.Optional;

@Controller
public class LibraryController {

    @Autowired
    private BookService bookService;

    @Autowired
    public LibraryController(final BookService pBookService) {
        this.bookService = pBookService;
    }

    @GetMapping("/mainPage")
    public String index() {
        return "main";
    }

    @GetMapping("/books")
    public String findAllBooks(final ModelMap model) {
        final List<Book> books = bookService.findAllBooks();

        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/userBooks")
    public String findAllUserBooks(final ModelMap model) {
        final List<Book> books = bookService.findAllBooks();

        model.addAttribute("books", books);
        return "user-books";
    }

    @GetMapping("/searchBook")
    public String searchBook(@Param("keyword") final String keyword, final ModelMap model) {
        final List<Book> books = bookService.searchBooks(keyword);

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "searched-books";
}

    @GetMapping("/book/{id}")
    public String findBookById(@PathVariable("id")final Long id, final ModelMap model) {
        final Optional<Book> book = bookService.findBookById(id);

        model.addAttribute("book", book);
        return "list-book";
    }

    @GetMapping("/add-book")
    public String add(final ModelMap model) {

        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/save")
    public String save(final ModelMap model, @ModelAttribute final Book book, final BindingResult errors) {

        bookService.save(book);

        return "redirect:books";
    }

    @GetMapping("/edit")
    public String edit(final ModelMap model, @RequestParam final Long id) {

        final Optional<Book> bookRecord = bookService.editBook(id);

        model.addAttribute("book", bookRecord.isPresent() ? bookRecord.get() : new Book());
        model.addAttribute("id", id);
        return "edit";
    }

    @GetMapping("/delete")
    public String delete(final ModelMap model, @RequestParam final Long id)  {

        final Optional<Book> bookRecord = bookService.editBook(id);

        model.addAttribute("book", bookRecord.isPresent() ? bookRecord.get() : new Book());
        model.addAttribute("id", id);
        return "delete";
    }

    @PostMapping("/delete")
    public String save(final ModelMap model, @RequestParam final Long id) {

        bookService.delete(id);

        return "redirect:books";
    }

}
