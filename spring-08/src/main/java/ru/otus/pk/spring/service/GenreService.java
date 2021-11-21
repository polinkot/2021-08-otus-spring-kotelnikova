package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.GenreDto;

import java.util.List;

public interface GenreService {

    Long count();

    List<GenreDto> getAll();

    Genre findById(String id);

    GenreDto getById(String id);

    Genre save(String id, String name);

    Genre save(Genre genre);

    void deleteById(String id);

    Genre findFirstByBooksId(String bookId);
}
