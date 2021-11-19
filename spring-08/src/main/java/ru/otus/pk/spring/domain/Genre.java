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
public class Genre {
    @Id
    private String id;

    private String name;

    private Set<Book> books = new HashSet<>();

    public Genre(String name, Book... books) {
        this.name = name;
        if (books.length > 0) {
            this.books = Set.of(books);
        }
    }
}
