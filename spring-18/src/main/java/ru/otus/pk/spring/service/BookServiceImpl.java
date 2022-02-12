package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;
import static ru.otus.pk.spring.resilience.Utils.failureForDemo;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND = "Book not found!!! id = %s";

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
        failureForDemo("ru.otus.pk.spring.service.BookServiceImpl.findById ");

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
}
