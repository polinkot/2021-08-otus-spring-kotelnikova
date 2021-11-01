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
    @Fetch(JOIN)
    @OneToMany(cascade = ALL, mappedBy = "author")
    private Set<Book> books = new HashSet<>();

    public Author(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addBooks(Set<Book> books) {
        books.forEach(book -> {
            boolean added = this.books.add(book);
            if (added) {
                book.setAuthor(this);
            }
        });
    }

    public void removeBooks(Set<Long> ids) {
        List<Book> books = this.books.stream()
                .filter(book -> ids.contains(book.getId()))
                .peek(book -> book.setAuthor(null))
                .collect(toList());
        this.books.removeAll(books);
    }

    public Set<Book> getBooks() {
        return unmodifiableSet(books);
    }
}
