package ru.otus.pk.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Author {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private Set<Book> books = new HashSet<>();

    public Author(String firstName, String lastName, Book... books) {
        this.firstName = firstName;
        this.lastName = lastName;

        if (books.length > 0) {
            this.books = Set.of(books);
        }
    }
}
