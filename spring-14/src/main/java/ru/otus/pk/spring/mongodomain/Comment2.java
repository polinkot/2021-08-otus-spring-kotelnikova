package ru.otus.pk.spring.mongodomain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment2 {
    private Long id;

    private String text;

    private Long book_id;
}
