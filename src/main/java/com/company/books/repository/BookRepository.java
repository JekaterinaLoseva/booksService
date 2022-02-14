package com.company.books.repository;

import com.company.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%" + " OR b.category LIKE %?1%" + " OR b.author LIKE %?1%")
    public List<Book> search(String keyword);
}
