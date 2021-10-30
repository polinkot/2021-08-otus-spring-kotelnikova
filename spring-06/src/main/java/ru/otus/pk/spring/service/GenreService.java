package ru.otus.pk.spring.service;

import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {

    long count();

    List<Genre> findAll();

    Genre findById(Long id);

    Genre save(Long id, String name, Set<Book> books);

    int deleteById(Long id);
}
