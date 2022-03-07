package com.company.books.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    private Long id;
    @Column(name = "title", nullable = false, length = 35)
    private String title;
    @Column(name = "author", nullable = false, length = 35)
    private String author;
    @Column(name = "link", nullable = false, length = 150)
    private String link;
    @Column(name = "category", nullable = false, length = 35)
    private String category;
    @Column(name = "year", nullable = false, length = 4)
    private String year;
    @Column(name= "available", nullable = false, length = 12)
    private boolean isAvailable;

    public Book() {
    }

    public Book(Long id, String title, String author, String link, String category, String year, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.link = link;
        this.category = category;
        this.year = year;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", link='" + link + '\'' +
                ", category='" + category + '\'' +
                ", year='" + year + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isAvailable == book.isAvailable &&
                Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(link, book.link) &&
                Objects.equals(category, book.category) &&
                Objects.equals(year, book.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, link, category, year, isAvailable);
    }
}
