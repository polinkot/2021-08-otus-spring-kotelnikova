package ru.otus.pk.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.pk.spring.domain.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
}
