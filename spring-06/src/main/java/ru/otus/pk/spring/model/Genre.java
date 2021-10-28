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
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Setter(value = NONE)
    @OneToMany(cascade = ALL, mappedBy = "genre")
    private List<Book> books = new ArrayList<>();

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setGenre(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setGenre(null);
    }

    public List<Book> getBooks() {
        return unmodifiableList(books);
    }
}
