package ru.otus.pk.spring.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private String id;

    private String firstName;

    private String lastName;
}
