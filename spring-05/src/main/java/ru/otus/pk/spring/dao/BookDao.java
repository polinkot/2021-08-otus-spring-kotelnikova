package ru.otus.pk.spring.dao;

import ru.otus.pk.spring.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    List<Book> getAll();

    Book getById(Long id);

    Long insert(Book book);

    int update(Book book);

    int deleteById(Long id);
}
