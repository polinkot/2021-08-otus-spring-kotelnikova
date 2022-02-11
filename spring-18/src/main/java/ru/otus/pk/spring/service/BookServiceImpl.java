package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND = "Book not found!!! id = %s";

    private static final Author AUTHOR = new Author(1L, "N/A", "N/A");
    private static final Genre GENRE = new Genre(1L, "N/A");

    private final BookRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        failureForDemo("ru.otus.pk.spring.service.BookServiceImpl.findAll ");

        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(BOOK_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Book save(Book book) {
        validate(book);
        return repository.save(book);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format(BOOK_NOT_FOUND, id), e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByAuthorId(Long authorId) {
        return repository.findByAuthorId(authorId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByGenreId(Long genreId) {
        return repository.findByGenreId(genreId);
    }

    private void validate(Book book) {
        if (isEmpty(book.getName())) {
            throw new LibraryException("Book name is null or empty!");
        }

        if (book.getAuthor() == null) {
            throw new LibraryException("Book author is null or empty!");
        }

        if (book.getGenre() == null) {
            throw new LibraryException("Book genre is null or empty!");
        }
    }

    @Override
    public List<Book> findAllFallbackCallNotPermittedException() {
        return List.of(new Book(1L, "CallNotPermittedException", AUTHOR, GENRE));
    }

    @Override
    public List<Book> findAllFallback() {
        return List.of(new Book(1L, "Exception", AUTHOR, GENRE));
    }

    private void failureForDemo(String method) {
        if (new Random().nextBoolean()) {
            System.out.println(method + " success");
            return;
        }

        if (new Random().nextBoolean()) {
            System.out.println(method + " Exception for Demo");
            throw new IllegalStateException(method + " IllegalStateException for Demo");
        }

        try {
            System.out.println(method + " delayed for Demo");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
