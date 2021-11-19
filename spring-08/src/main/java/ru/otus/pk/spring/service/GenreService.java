package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    Long count();

    List<Genre> findAll();

    Genre findById(String id);


    Genre save(String id, String name);

    Genre save(Genre genre);

//    void deleteById(Long id);

    Genre findFirstByBooksId(String bookId);
}
