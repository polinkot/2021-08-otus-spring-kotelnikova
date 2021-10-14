package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    int count();

    List<Author> getAll();

    Author getById(Long id);

    Number insert(String firstName, String lastName);

    int update(Long id, String firstName, String lastName);

    int deleteById(Long id);
}
