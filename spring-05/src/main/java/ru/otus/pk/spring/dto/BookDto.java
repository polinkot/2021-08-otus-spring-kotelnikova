package ru.otus.pk.spring.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;

@RequiredArgsConstructor
@Data
public class BookDto extends Book {
    private final Book book;
    private final Author author;
    private final Genre genre;
}