package ru.otus.pk.spring.dao;

import ru.otus.pk.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    List<Genre> getAll();

    Genre getById(Long id);

    Number insert(Genre genre);

    int update(Genre genre);

    int deleteById(Long id);
}
