package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Genre;

import java.util.List;
import java.util.Map;

public interface GenreService {

    Map<String, Genre> createGenres(List list);
}
