package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.BookDto;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND = "Book not found!!! id = %s";

    private final BookRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAll() {
        return repository.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto getById(String id) {
        return repository.getById(id).orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, id)));
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(String id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public BookDto add(String name,
                       String authorId, String authorFirstName, String authorLastName,
                       String genreId, String genreName) {
        Book book = new Book(name);
        validate(book);
        Book savedBook = repository.save(book);

        Author author = authorId != null ?
                authorService.findById(authorId) :
                new Author(authorFirstName, authorLastName);
        author.getBooks().add(savedBook);
        authorService.save(author);

        Genre genre = genreId != null ?
                genreService.findById(genreId) :
                new Genre(genreName);
        genre.getBooks().add(savedBook);
        genreService.save(genre);

        return getById(savedBook.getId());
    }

    @Transactional
    @Override
    public BookDto edit(String id, String name) {
        Book book = findById(id);
        book.setName(name);
        validate(book);

        Author author = authorService.findFirstByBooksId(id);
        author.getBooks().stream().filter(b -> b.getId().equals(id)).forEach(b -> b.setName(name));

        Genre genre = genreService.findFirstByBooksId(id);
        genre.getBooks().stream().filter(b -> b.getId().equals(id)).forEach(b -> b.setName(name));

        repository.save(book);
        authorService.save(author);
        genreService.save(genre);

        return getById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Book book = findById(id);
        commentService.deleteAll(book.getComments());

        repository.deleteById(id);

        Author author = authorService.findFirstByBooksId(id);
        author.getBooks().removeIf(b -> b.getId().equals(id));
        authorService.save(author);

        Genre genre = genreService.findFirstByBooksId(id);
        genre.getBooks().removeIf(b -> b.getId().equals(id));
        genreService.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getByAuthorId(String authorId) {
        Author author = authorService.findById(authorId);
        return repository.getBooks(author.getBooks().stream().map(Book::getId).collect(toSet()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getByGenreId(String genreId) {
        Genre genre = genreService.findById(genreId);
        return repository.getBooks(genre.getBooks().stream().map(Book::getId).collect(toSet()));
    }

    private void validate(Book book) {
        if (isEmpty(book.getName())) {
            throw new LibraryException("Book name is null or empty!");
        }
    }
}
