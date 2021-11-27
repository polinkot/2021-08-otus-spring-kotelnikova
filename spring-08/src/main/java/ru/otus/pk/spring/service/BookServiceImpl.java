package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static ru.otus.pk.spring.service.AuthorServiceImpl.AUTHOR_NOT_FOUND;
import static ru.otus.pk.spring.service.GenreServiceImpl.GENRE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND = "Book not found!!! id = %s";

    private final BookRepository repository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(String id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Book add(String name,
                    String authorId, String authorFirstName, String authorLastName,
                    String genreId, String genreName) {
        Book book = new Book();
        book.setName(name);

        Author author = authorId != null ?
                repository.findFirstByAuthorId(authorId)
                        .orElseThrow(() -> new LibraryException(format(AUTHOR_NOT_FOUND, authorId)))
                        .getAuthor() :
                new Author(generateId(repository::findFirstByAuthorId), authorFirstName, authorLastName);
        book.setAuthor(author);

        Genre genre = genreId != null ?
                repository.findFirstByGenreId(genreId)
                        .orElseThrow(() -> new LibraryException(format(GENRE_NOT_FOUND, genreId)))
                        .getGenre() :
                new Genre(generateId(repository::findFirstByGenreId), genreName);
        book.setGenre(genre);

        validate(book);
        return repository.save(book);
    }

    @Transactional
    @Override
    public Book edit(String id, String name) {
        Book book = findById(id);
        book.setName(name);
        validate(book);
        return repository.save(book);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        commentService.deleteByBookId(id);
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByAuthorId(String authorId) {
        return repository.findByAuthorId(authorId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByGenreId(String genreId) {
        return repository.findByGenreId(genreId);
    }

    private String generateId(Function<String, Optional<Book>> checkIdInDb) {
        while (true) {
            String id = ObjectId.get().toString();
            if (checkIdInDb.apply(id).isEmpty()) {
                return id;
            }
        }
    }

    private void validate(Book book) {
        List<String> errors = new ArrayList<>();

        if (isEmpty(book.getName())) {
            errors.add("Book name is null or empty!");
        }

        if (isEmpty(book.getAuthor())) {
            errors.add("Book author is null!");
        }

        if (isEmpty(book.getAuthor().getFirstName())) {
            errors.add("Book author first name is null or empty!");
        }

        if (isEmpty(book.getAuthor().getLastName())) {
            errors.add("Book author last name is null or empty!");
        }

        if (isEmpty(book.getGenre())) {
            errors.add("Book genre is null!");
        }

        if (isEmpty(book.getGenre().getName())) {
            errors.add("Book genre name is null or empty!");
        }

        if (!isEmpty(errors)) {
            throw new LibraryException(join("\n", errors));
        }
    }
}
