package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;

import java.util.Set;

public interface AuthorService {

    Integer count();

    Set<Author> findAll();

    Author findById(String id);

    void save(String id, String firstName, String lastName);

    void deleteById(String id);
}
