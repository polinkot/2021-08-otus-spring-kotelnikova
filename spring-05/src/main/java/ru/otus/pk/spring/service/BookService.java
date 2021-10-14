package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Book;

import java.util.List;

public interface BookService {

    int count();

    List<Book> getAll();

    Book getById(Long id);

    Book getByIdComplete(Long id);

    Number insert(String name, Long authorId, Long genreId);

    int update(Long id, String name, Long authorId, Long genreId);

    int deleteById(Long id);
}
