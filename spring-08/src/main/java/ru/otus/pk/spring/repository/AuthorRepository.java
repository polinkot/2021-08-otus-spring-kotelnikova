package ru.otus.pk.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.pk.spring.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Author findFirstByBooksId(String bookId);
}
