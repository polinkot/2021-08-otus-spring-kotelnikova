package ru.otus.pk.spring.mongodomain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book2 {
    private Long id;

    private String name;

    private Long author_id;

    private Long genre_id;
}
