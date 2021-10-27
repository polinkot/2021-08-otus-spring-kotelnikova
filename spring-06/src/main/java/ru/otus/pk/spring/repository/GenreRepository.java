package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findById(Long id);

    List<Genre> findAll();

    Genre save(Genre Genre);

    int deleteById(Long id);
}
