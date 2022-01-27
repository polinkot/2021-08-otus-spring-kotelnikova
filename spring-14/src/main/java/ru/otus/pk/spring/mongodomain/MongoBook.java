package ru.otus.pk.spring.mongodomain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MongoBook {
    @Id
    private String id;

    private String name;

    private MongoAuthor author;

    private MongoGenre genre;
}
