package ru.otus.pk.spring.mongodomain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoAuthor {
    private String id;

    private String firstName;

    private String lastName;
}
