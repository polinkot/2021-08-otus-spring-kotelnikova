package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    public static final String GENRE_NOT_FOUND = "Genre not found!!! id = %s";

    private final BookRepository bookRepository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    @Override
    public Integer count() {
        return bookRepository.findAllGenres().size();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return bookRepository.findAllGenres();
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(String id) {
        Book book = bookRepository.findFirstByGenreId(id)
                .orElseThrow(() -> new LibraryException(format(GENRE_NOT_FOUND, id)));
        return book.getGenre();
    }

    @Transactional
    @Override
    public void save(String id, String name) {
        List<Book> books = bookRepository.findByGenreId(id);
        books.forEach(book -> {
            Genre genre = book.getGenre();
            genre.setName(name);
            validate(genre);
        });
        bookRepository.saveAll(books);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        List<Book> books = bookRepository.findByGenreId(id);
        books.forEach(book -> commentService.deleteByBookId(book.getId()));
        bookRepository.deleteAll(books);
    }

    private void validate(Genre genre) {
        if (isEmpty(genre.getName())) {
            throw new LibraryException("Genre name is null or empty!");
        }
    }
}
