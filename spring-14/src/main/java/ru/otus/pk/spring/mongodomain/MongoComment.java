package ru.otus.pk.spring.mongodomain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MongoComment {
    @Id
    private String id;

    private String text;

    private MongoBook mongoBook;
}
