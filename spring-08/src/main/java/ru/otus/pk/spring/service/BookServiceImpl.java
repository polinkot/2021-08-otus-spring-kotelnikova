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

//    @Transactional
//    @Override
//    public BookDto add(String name,
//                       String authorId, String authorFirstName, String authorLastName,
//                       String genreId, String genreName) {
//        Book book = new Book(name);
//        validate(book);
//
//        Author author = authorService.getOrCreate(authorId, authorFirstName, authorLastName);
//        if (author == null) {
//            throw new LibraryException("Book author is null or empty!");
//        }
//
//        Genre genre = genreService.getOrCreate(genreId, genreName);
//        if (genre == null) {
//            throw new LibraryException("Book genre is null or empty!");
//        }
//
//        Book savedBook = repository.save(book);
//        author.getBooks().add(savedBook);
//        authorService.save(author);
//
//        genre.getBooks().add(savedBook);
//        genreService.save(genre);
//
//        return getById(savedBook.getId());
//    }

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

//    @Transactional(readOnly = true)
//    @Override
//    public List<Book> findByAuthorId(Long authorId) {
//        return repository.findByAuthorId(authorId);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public List<Book> findByGenreId(Long genreId) {
//        return repository.findByGenreId(genreId);
//    }

    private void validate(Book book) {
        if (isEmpty(book.getName())) {
            throw new LibraryException("Book name is null or empty!");
        }

//        if (book.getAuthor() == null) {
//            throw new LibraryException("Book author is null or empty!");
//        }
//
//        if (book.getGenre() == null) {
//            throw new LibraryException("Book genre is null or empty!");
//        }
    }


//    @Transactional(readOnly = true)
//    @Override
//    public List<BookDto> getAll() {
//
//        List<BookDto> all2 = repository.getAll2();
//        List<BookDto> all3 = repository.getAll3();
//
//        List<Author> authors = authorService.findAll();
//        List<Genre> genres = genreService.findAll();
//
//        Map<String, GenreDto> bookGenres = genres.stream().map(genre -> genre.getBooks().stream()
//                .collect(toMap(Book::getId, book -> genre)))
//                .flatMap(x -> x.entrySet().stream())
//                .collect(toMap(Map.Entry::getKey, entry -> new GenreDto(entry.getValue().getId(), entry.getValue().getName())));
//
//        return authors.stream()
//                .map(author -> author.getBooks().stream()
//                        .map(book -> new BookDto(book.getId(), book.getName(),
//                                new AuthorDto(author.getId(), author.getFirstName(), author.getLastName()),
//                                bookGenres.get(book.getId())))
//                        .collect(toList()))
//                .flatMap(Collection::stream)
//                .collect(toList());
//    }

}
