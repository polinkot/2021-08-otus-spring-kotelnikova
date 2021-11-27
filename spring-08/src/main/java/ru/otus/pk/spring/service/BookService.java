package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Book;

import java.util.List;

public interface BookService {

    Long count();

    List<Book> findAll();

    Book findById(String id);

    Book add(String name,
             String authorId, String authorFirstName, String authorLastName,
             String genreId, String genreName);

    Book edit(String id, String name);

    void deleteById(String id);

    List<Book> findByAuthorId(String authorId);

    List<Book> findByGenreId(String genreId);
}
