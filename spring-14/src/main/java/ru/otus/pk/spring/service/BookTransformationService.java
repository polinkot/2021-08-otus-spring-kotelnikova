package ru.otus.pk.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.mongodomain.*;

@Service
public class BookTransformationService {

    public Book transform(MongoBook mongoBook) {
        MongoAuthor mongoAuthor = mongoBook.getAuthor();
        MongoGenre mongoGenre = mongoBook.getGenre();

        return new Book(null,
                mongoBook.getName(),
                new Author(null, mongoAuthor.getFirstName(), mongoAuthor.getLastName(), mongoAuthor.getId()),
                new Genre(null, mongoGenre.getName(), mongoGenre.getId()));
    }
}
