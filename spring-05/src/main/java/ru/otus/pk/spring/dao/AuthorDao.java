package ru.otus.pk.spring.dao;

import ru.otus.pk.spring.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    List<Author> getAll();

    Author getById(Long id);

    Long insert(Author author);

    int update(Author author);

    int deleteById(Long id);
}
