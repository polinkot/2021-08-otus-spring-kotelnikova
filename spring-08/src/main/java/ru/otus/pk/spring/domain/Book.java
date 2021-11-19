package ru.otus.pk.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Book {

    @Id
    private String id;

    private String name;

    private List<Comment> comments = new ArrayList<>();

    public Book(String name, Comment... comments) {
        this.name = name;
        this.comments = List.of(comments);
    }
}
