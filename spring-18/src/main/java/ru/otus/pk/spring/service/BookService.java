package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Book;

import java.util.List;

public interface BookService {

    Long count();

    List<Book> findAll();

    Book findById(Long id);

    Book save(Book book);

    void deleteById(Long id);

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByGenreId(Long genreId);
}
