package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.GenreDto;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.AuthorRepository;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;
import ru.otus.pk.spring.repository.GenreRepository;

import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    public static final String GENRE_NOT_FOUND = "Genre not found!!! id = %s";

    private final GenreRepository repository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getAll() {
        return repository.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(String id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(GENRE_NOT_FOUND, id)));
    }

    @Transactional(readOnly = true)
    @Override
    public GenreDto getById(String id) {
        return repository.getById(id).orElseThrow(() -> new LibraryException(format(GENRE_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Genre save(String id, String name) {
        Genre genre = id != null ? findById(id) : new Genre();

        genre.setName(name);

        validate(genre);
        return repository.save(genre);
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        validate(genre);
        return repository.save(genre);
    }


    @Transactional
    @Override
    public void deleteById(String id) {
        Genre genre = findById(id);

        genre.getBooks().forEach(book -> {
            commentRepository.deleteAll(book.getComments());

            Author author = authorRepository.findFirstByBooksId(book.getId());
            author.getBooks().removeIf(b -> b.getId().equals(book.getId()));
            authorRepository.save(author);
        });

        bookRepository.deleteAll(genre.getBooks());

        repository.deleteById(id);
    }

    @Override
    public Genre findFirstByBooksId(String bookId) {
        return repository.findFirstByBooksId(bookId);
    }

    private void validate(Genre genre) {
        if (isEmpty(genre.getName())) {
            throw new LibraryException("Genre name is null or empty!");
        }
    }
}
