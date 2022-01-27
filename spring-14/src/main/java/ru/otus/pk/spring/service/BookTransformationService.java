package ru.otus.pk.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.mongodomain.MongoBook;

import java.util.Random;

@Service
public class BookTransformationService {

    public Book transform(MongoBook mongoBook) {
        return new Book(null,
                mongoBook.getName(),
                new Author(null,
                        mongoBook.getMongoAuthor().getFirstName(),
                        mongoBook.getMongoAuthor().getLastName(),
                        mongoBook.getMongoAuthor().getId()),
                new Genre(null,
                        mongoBook.getMongoGenre().getName() + new Random().nextInt()));
    }
}
