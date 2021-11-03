package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Genre;
import ru.otus.pk.spring.repository.GenreRepository;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    public static final String GENRE_NOT_FOUND = "Genre not found!!! id = %s";

    private final GenreRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(GENRE_NOT_FOUND, id)));
    }

    @Transactional(readOnly = true)
    @Override
    public Genre createNew(String name) {
        Genre genre = new Genre(null, name);
        validate(genre);
        return genre;
    }

    @Transactional
    @Override
    public Genre save(Long id, String name) {
        Genre genre = id != null ? findById(id) : new Genre();

        genre.setName(name);

        validate(genre);
        return repository.save(genre);
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findBooks(Long id) {
        return repository.findBooks(id);
    }

    private void validate(Genre genre) {
        if (isEmpty(genre.getName())) {
            throw new LibraryException("Genre name is null or empty!");
        }
    }
}
