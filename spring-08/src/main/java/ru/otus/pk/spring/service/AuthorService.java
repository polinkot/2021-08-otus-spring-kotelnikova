package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    Long count();

    List<Author> findAll();

    Author findById(String id);

    Author save(String id, String firstName, String lastName);

    Author save(Author author);

    void deleteById(String id);

    Author findFirstByBooksId(String bookId);
}
