package ru.otus.pk.spring.service;

import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Genre;

import java.util.List;

public interface GenreService {

    Long count();

    List<Genre> findAll();

    Genre findById(Long id);

    Genre createNew(String name);

    Genre save(Long id, String name);

    int deleteById(Long id);

    List<Book> findBooks(Long id);
}
