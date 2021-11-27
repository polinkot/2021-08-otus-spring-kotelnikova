package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    Long count();

    List<Author> findAll();

    Author findById(Long id);

    Author createNew(String firstName, String lastName);

    Author save(Long id, String firstName, String lastName);

    void deleteById(Long id);
}
