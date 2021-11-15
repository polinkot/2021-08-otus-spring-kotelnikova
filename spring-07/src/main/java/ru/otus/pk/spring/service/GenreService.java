package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    Long count();

    List<Genre> findAll();

    Genre findById(Long id);

    Genre createNew(String name);

    Genre save(Long id, String name);

    void deleteById(Long id);
}
