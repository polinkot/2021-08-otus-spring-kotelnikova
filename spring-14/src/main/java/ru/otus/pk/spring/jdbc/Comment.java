package ru.otus.pk.spring.jdbc;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;

    private String text;

    private Long book_id;
}
