package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;

import java.util.Set;

public interface BookRepositoryCustom {
    Set<Author> findAllAuthors();

    Set<Genre> findAllGenres();
}
