package ru.otus.pk.spring.service;

import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;

import java.util.List;

public interface AuthorService {

    Long count();

    List<Author> findAll();

    Author findById(Long id);

    Author createNew(String firstName, String lastName);

    Author save(Long id, String firstName, String lastName);

    int deleteById(Long id);

    List<Book> findBooks(Long id);
}
