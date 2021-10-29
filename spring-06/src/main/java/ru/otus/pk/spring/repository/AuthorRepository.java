package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Long count();

    Optional<Author> findById(Long id);

    List<Author> findAll();

    Author save(Author author);

    int deleteById(Long id);
}
