package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Genre;

import java.util.List;

public interface GenreService {
    int count();

    List<Genre> getAll();

    Genre getById(Long id);

    Number insert(String name);

    int update(Long id, String name);

    int deleteById(Long id);
}
