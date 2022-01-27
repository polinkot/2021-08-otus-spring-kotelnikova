package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;

import java.util.List;
import java.util.Map;

public interface AuthorService {

    Map<String, Author> createAuthors(List list);
}
