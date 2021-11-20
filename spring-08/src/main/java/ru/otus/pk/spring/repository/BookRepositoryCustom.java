package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepositoryCustom {
    List<BookDto> getAll();

    Optional<BookDto> getById(String bookId);

    List<BookDto> getBooks(Set<String> bookIds);
}
