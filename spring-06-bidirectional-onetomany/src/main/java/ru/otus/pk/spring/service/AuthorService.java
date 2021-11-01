package ru.otus.pk.spring.service;


import ru.otus.pk.spring.model.Author;

import java.util.List;

public interface AuthorService {

    long count();

    List<Author> findAll();

    Author findById(Long id);

    Author save(Long id, String firstName, String lastName);

    int deleteById(Long id);
}
