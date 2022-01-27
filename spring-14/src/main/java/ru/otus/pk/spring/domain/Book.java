package ru.otus.pk.spring.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@NamedEntityGraph(name = "Book.Author.Genre", attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"author", "genre"})
@EqualsAndHashCode(exclude = {"author", "genre"})
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    private Author author;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    private Genre genre;
}
