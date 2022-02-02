package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    Long count();

    List<Genre> findAll();

    Genre findById(Long id);

    Genre save(Genre genre);

    void deleteById(Long id);
}
