package ru.otus.pk.spring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.FetchMode.JOIN;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"author", "genre"})
@ToString(exclude = {"author", "genre"})
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = LAZY)
    private Author author;

    @ManyToOne(fetch = LAZY)
    private Genre genre;

    @Fetch(JOIN)
    @BatchSize(size = 25)
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private Set<Comment> comments = new HashSet<>();

    public Book(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        setAuthor(author);
        setGenre(genre);
    }

    public void setAuthor(Author author) {
        if (author == null) {
            this.author = null;
            return;
        }

        this.author = author;
        author.addBooks(Set.of(this));
    }

    public void setGenre(Genre genre) {
        if (genre == null) {
            this.genre = null;
            return;
        }

        this.genre = genre;
        genre.addBooks(Set.of(this));
    }
}
