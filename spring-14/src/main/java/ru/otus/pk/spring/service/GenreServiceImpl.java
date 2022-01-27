package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.repository.GenreRepository;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Transactional
    @Override
    public Map<String, Genre> createGenres(List list) {
        Map<String, Genre> genres = new HashMap<>();
        new ArrayList<Book>(list)
                .forEach(book -> genres.put(book.getGenre().getMongoId(), book.getGenre()));

        repository.findByMongoIdIn(genres.keySet())
                .forEach(genre -> genres.put(genre.getMongoId(), genre));

        Set<Genre> newGenres = genres.values().stream()
                .filter(genre -> genre.getId() == null).collect(toSet());
        repository.saveAll(newGenres)
                .forEach(genre -> genres.put(genre.getMongoId(), genre));

        return genres;
    }
}
