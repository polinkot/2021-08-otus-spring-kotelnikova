package ru.otus.pk.spring.jdbc;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;

    private String name;

    private Long authorId;

    private Long genreId;

    private String mongoId;
}
