package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.dao.GenreDao;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.exception.LibraryException;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    @Override
    public List<Genre> getAll() {
        return dao.getAll();
    }

    @Override
    public int count() {
        return dao.count();
    }

    @Override
    public Genre getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public Long insert(String name) {
        Genre genre = new Genre(null, name);
        validate(genre);
        return dao.insert(genre);
    }

    @Override
    public int update(Long id, String name) {
        Genre genre = new Genre(id, name);
        ofNullable(id).orElseThrow(() -> new LibraryException("Genre id is null!!!"));
        validate(genre);
        return dao.update(genre);
    }

    @Override
    public int deleteById(Long id) {
        return dao.deleteById(id);
    }

    private void validate(Genre genre) {
        if (isEmpty(genre.getName())) {
            throw new LibraryException("Genre name is null or empty!");
        }
    }
}