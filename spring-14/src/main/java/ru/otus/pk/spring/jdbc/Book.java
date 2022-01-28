package ru.otus.pk.spring.jdbc;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;

    private String name;

    private Long author_id;

    private Long genre_id;

    private String mongo_id;
}
