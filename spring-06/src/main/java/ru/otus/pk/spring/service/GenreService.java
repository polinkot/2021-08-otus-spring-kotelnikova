package ru.otus.pk.spring.service;

import ru.otus.pk.spring.model.Genre;

import java.util.List;

public interface GenreService {

    long count();

    List<Genre> findAll();

    Genre findById(Long id);

    Genre save(Long id, String name);

    int deleteById(Long id);
}
