package ru.otus.pk.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.pk.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    Optional<Book> findFirstByAuthorId(String authorId);

    Optional<Book> findFirstByGenreId(String genreId);

    List<Book> findByAuthorId(String authorId);

    List<Book> findByGenreId(String genreId);
}
