package ru.otus.pk.spring.service;

import ru.otus.pk.spring.model.Book;

import java.util.List;

public interface BookService {

    long count();

    List<Book> findAll();

    Book findById(Long id);

    Book save(Long id, String name, Long authorId, Long genreId);

    int deleteById(Long id);
}
