package com.company.books.service;

import com.company.books.model.Book;
import com.company.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(final BookRepository pRepository) {
        this.bookRepository = pRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(final Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> searchBooks(final String keyword) {
        return bookRepository.search(keyword);
    }

    public Optional<Book> editBook(final Long id) {
        return bookRepository.findById(id);
    }

    public Book save(final Book book) {
        return bookRepository.save(book);
    }

    public void delete(final Long id) {
        bookRepository.deleteById(id);
    }

}
