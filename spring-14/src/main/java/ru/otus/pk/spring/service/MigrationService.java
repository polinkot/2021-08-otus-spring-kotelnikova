package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.repository.*;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class MigrationService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Map<String, Author> createAuthors(List<Book> books) {
        Map<String, Author> authors = new HashMap<>();
        books.forEach(book -> authors.put(book.getAuthor().getMongoId(), book.getAuthor()));

        authorRepository.findByMongoIdIn(authors.keySet())
                .forEach(author -> authors.put(author.getMongoId(), author));

        Set<Author> newAuthors = authors.values().stream()
                .filter(author -> author.getId() == null).collect(toSet());
        authorRepository.saveAll(newAuthors)
                .forEach(author -> authors.put(author.getMongoId(), author));

        return authors;
    }

    @Transactional
    public Map<String, Genre> createGenres(List<Book> books) {
        Map<String, Genre> genres = new HashMap<>();
        books.forEach(book -> genres.put(book.getGenre().getMongoId(), book.getGenre()));

        genreRepository.findByMongoIdIn(genres.keySet())
                .forEach(genre -> genres.put(genre.getMongoId(), genre));

        Set<Genre> newGenres = genres.values().stream()
                .filter(genre -> genre.getId() == null).collect(toSet());
        genreRepository.saveAll(newGenres)
                .forEach(genre -> genres.put(genre.getMongoId(), genre));

        return genres;
    }

    @Transactional(readOnly = true)
    public Map<String, Book> findBooks(List<Comment> comments) {
        Map<String, Book> books = new HashMap<>();
        comments.forEach(comment -> books.put(comment.getBook().getMongoId(), comment.getBook()));

        bookRepository.findByMongoIdIn(books.keySet())
                .forEach(book -> books.put(book.getMongoId(), book));

        return books;
    }
}
