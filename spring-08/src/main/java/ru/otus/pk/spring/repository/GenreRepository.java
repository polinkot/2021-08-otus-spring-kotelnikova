package ru.otus.pk.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.pk.spring.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Genre findFirstByBooksId(String bookId);
}
