package ru.otus.pk.spring.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Comment {
    @Id
    private String id;

    private String text;

    private Book book;
}
