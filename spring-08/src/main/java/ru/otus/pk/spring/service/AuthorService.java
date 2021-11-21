package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    Long count();

    List<AuthorDto> getAll();

    Author findById(String id);

    AuthorDto getById(String id);

    Author save(String id, String firstName, String lastName);

    Author save(Author author);

    void deleteById(String id);

    Author findFirstByBooksId(String bookId);
}
