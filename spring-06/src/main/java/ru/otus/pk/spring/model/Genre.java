package ru.otus.pk.spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;
import static org.hibernate.annotations.FetchMode.JOIN;

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
    @Fetch(JOIN)
    @OneToMany(cascade = ALL, mappedBy = "genre")
    private Set<Book> books = new HashSet<>();

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addBooks(Set<Book> books) {
        books.forEach(book -> {
            boolean added = this.books.add(book);
            if (added) {
                book.setGenre(this);
            }
        });
    }

    public void removeBooks(Set<Long> ids) {
        List<Book> books = this.books.stream()
                .filter(book -> ids.contains(book.getId()))
                .peek(book -> book.setGenre(null))
                .collect(toList());
        this.books.removeAll(books);
    }

    public Set<Book> getBooks() {
        return unmodifiableSet(books);
    }
}
