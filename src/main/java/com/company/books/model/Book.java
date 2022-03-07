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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
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
    @Column(name = "available", nullable = false, length = 12)
    private boolean isAvailable;

    public Book() {
    }

    public Book(final Long pId, final String pTitle, final String pAuthor, final String pLink,
                final String pCategory, final String pYear, final boolean pIsAvailable) {
        this.id = pId;
        this.title = pTitle;
        this.author = pAuthor;
        this.link = pLink;
        this.category = pCategory;
        this.year = pYear;
        this.isAvailable = pIsAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long pId) {
        this.id = pId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String pTitle) {
        this.title = pTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String pAuthor) {
        this.author = pAuthor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String pLink) {
        this.link = pLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String pCategory) {
        this.category = pCategory;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String pYear) {
        this.year = pYear;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(final boolean pIsAvailable) {
        isAvailable = pIsAvailable;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", author='" + author + '\''
                + ", link='" + link + '\''
                + ", category='" + category + '\''
                + ", year='" + year + '\''
                + ", isAvailable=" + isAvailable
                + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return isAvailable == book.isAvailable
                && Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(link, book.link)
                && Objects.equals(category, book.category)
                && Objects.equals(year, book.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, link, category, year, isAvailable);
    }
}
