package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;

import java.util.List;

public interface BookRepositoryCustom {
    List<Author> findAllAuthors();

    List<Genre> findAllGenres();
}
