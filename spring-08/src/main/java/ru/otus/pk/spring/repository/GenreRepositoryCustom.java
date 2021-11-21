package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.dto.GenreDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreRepositoryCustom {
    List<GenreDto> getAll();

    Optional<GenreDto> getById(String genreId);

    List<GenreDto> getGenres(Set<String> genreIds);
}
