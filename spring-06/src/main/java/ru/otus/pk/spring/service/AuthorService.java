package ru.otus.pk.spring.service;


import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    long count();

    List<Author> findAll();

    Author findById(Long id);

    Author save(Long id, String firstName, String lastName, Set<Book> books);

    int deleteById(Long id);
}
