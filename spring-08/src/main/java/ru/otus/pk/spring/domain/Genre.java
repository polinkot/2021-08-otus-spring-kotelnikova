package ru.otus.pk.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Genre {
    @Id
    private String id;

    private String name;

    private List<Book> books;

    public Genre(String name, Book... books) {
        this.name = name;
        this.books = List.of(books);
    }
}
