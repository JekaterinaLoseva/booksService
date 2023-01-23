package com.company.books.repository;

import com.company.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// interface that extends JpaRepository for the CRUD methods
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // define custom query
    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%" + " OR b.category LIKE %?1%" + " OR b.author LIKE %?1%")
    List<Book> search(String keyword);
}
