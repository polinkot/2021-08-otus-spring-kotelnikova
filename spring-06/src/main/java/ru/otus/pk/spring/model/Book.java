package ru.otus.pk.spring.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;

@NamedEntityGraph(name = "Book.plain")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"author", "genre"})
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = PERSIST)
    private Author author;

    @ManyToOne(cascade = PERSIST)
    private Genre genre;
}
