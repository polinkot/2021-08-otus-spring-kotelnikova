package ru.otus.pk.spring.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Book {
    @Id
    private String id;

    private String name;

    private Author author;

    private Genre genre;
}
