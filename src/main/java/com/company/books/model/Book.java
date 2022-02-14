package com.company.books.model;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title", nullable= false, length = 35)
    private String title;
    @Column(name = "author", nullable= false, length = 35)
    private String author;
    @Column(name = "link", nullable= false, length = 150)
    private String link;
    @Column(name = "category", nullable= false, length = 35)
    private String category;
    @Column(name = "year", nullable= false, length = 4)
    private String year;
    @Column(nullable= false, length = 5)
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
}
