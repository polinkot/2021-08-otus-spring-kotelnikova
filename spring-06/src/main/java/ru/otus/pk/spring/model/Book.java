package ru.otus.pk.spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = LAZY)
    private Author author;

    @ToString.Exclude
    @ManyToOne(fetch = LAZY)
    private Genre genre;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Comment> comments = new ArrayList<>();

    public Book(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
