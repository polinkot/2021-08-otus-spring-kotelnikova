package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    Integer count();

    List<Author> findAll();

    Author findById(String id);

    void save(String id, String firstName, String lastName);

    void deleteById(String id);
}
