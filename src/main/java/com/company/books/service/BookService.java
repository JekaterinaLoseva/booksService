package com.company.books.service;

import com.company.books.model.Book;
import com.company.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//define logic of service
@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    public BookService(final BookRepository pRepository) {
        this.bookRepository = pRepository;
    }

    // get all books records by using the method findAll()
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    // get a specific record by using the method findById()
    public Optional<Book> findBookById(final Long id) {
        return bookRepository.findById(id);
    }

    // search a record by keyword using the method search()
    public List<Book> searchBooks(final String keyword) {
        return bookRepository.search(keyword);
    }

    // update a record by using the method findById()
    public Optional<Book> editBook(final Long id) {
        return bookRepository.findById(id);
    }

    // save a specific record by using the method save()
    public Book save(final Book book) {
        return bookRepository.save(book);
    }

    // delete a specific record by using the method deleteById()
    public void delete(final Long id) {
        bookRepository.deleteById(id);
    }

}
