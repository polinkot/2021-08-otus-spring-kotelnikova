package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Genre;

import java.util.Set;

public interface GenreService {

    Integer count();

    Set<Genre> findAll();

    Genre findById(String id);

    void save(String id, String name);

    void deleteById(String id);
}
