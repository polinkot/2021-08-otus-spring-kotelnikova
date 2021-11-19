package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.GenreRepository;

import java.util.List;

import static java.lang.String.format;

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
    public Genre findById(String id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(GENRE_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Genre save(String id, String name) {
        Genre genre = id != null ? findById(id) : new Genre();

        genre.setName(name);

//        validate(genre);
        return repository.save(genre);
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
//        validate(genre);
        return repository.save(genre);
    }


    //    @Transactional
//    @Override
//    public void deleteById(Long id) {
//        repository.deleteById(id);
//    }
//

    @Override
    public Genre findFirstByBooksId(String bookId) {
        return repository.findFirstByBooksId(bookId);
    }


//    private void validate(Genre genre) {
//        if (isEmpty(genre.getName())) {
//            throw new LibraryException("Genre name is null or empty!");
//        }
//    }
}
