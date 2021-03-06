package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Long count();

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book save(Book book);

    int deleteById(Long id);
}
