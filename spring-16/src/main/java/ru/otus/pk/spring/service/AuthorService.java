package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    Long count();

    List<Author> findAll();

    Author findById(Long id);

    Author save(Author author);

    void deleteById(Long id);
}
