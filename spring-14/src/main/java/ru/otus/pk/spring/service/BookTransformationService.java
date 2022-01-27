package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.mongodomain.MongoBook;

import javax.persistence.EntityManager;
import java.util.Random;

@Service
public class BookTransformationService {

//    @Autowired
//    private EntityManager entityManager;

//    public static Author AUTHOR = new Author(null, "book.getAuthor().getFirstName()", "book.getAuthor().getLastName()");

//    static Long ID = 1L;

    public Book transform(MongoBook mongoBook) {

        Author author = new Author(null,
                mongoBook.getMongoAuthor().getFirstName(),
                mongoBook.getMongoAuthor().getLastName(),
                mongoBook.getMongoAuthor().getId());

        Genre genre = new Genre(null,
                mongoBook.getMongoGenre().getName() + new Random().nextInt());

        return new Book(null, mongoBook.getName(), author, genre);
    }
}
