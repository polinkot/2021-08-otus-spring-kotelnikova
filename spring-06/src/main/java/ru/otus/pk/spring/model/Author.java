package ru.otus.pk.spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@NoArgsConstructor
@Data
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Setter(value = NONE)
    @OneToMany(cascade = ALL, mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    public Author(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setAuthor(null);
    }

    public List<Book> getBooks() {
        return unmodifiableList(books);
    }
}
