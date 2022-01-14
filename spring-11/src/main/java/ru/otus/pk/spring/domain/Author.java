package ru.otus.pk.spring.domain;

import lombok.*;

import static java.lang.String.format;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private String id;

    private String firstName;

    private String lastName;

    public String getFullName() {
        return format("%s %s", firstName, lastName);
    }
}
