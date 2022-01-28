package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    Map<String, Book> findBooks(List list);
}
