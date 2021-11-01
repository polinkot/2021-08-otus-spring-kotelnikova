package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Comment;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND = "Book not found!!! id = %s";

    private final BookRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Book save(Long id, String name, Long authorId, Long genreId) {
        Book book = id != null ?
                repository.findById(id).orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, id))) :
                new Book();

        book.setName(name);
        book.setAuthor(authorService.findById(authorId));
        book.setGenre(genreService.findById(genreId));

        validate(book);
        return repository.save(book);
    }

    @Transactional
    @Override
    public Book addComment(Long id, String comment) {
        Book book = repository.findById(id).orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, id)));
        book.getComments().add(new Comment(null, comment));

        validate(book);
        return repository.save(book);
    }

    @Transactional
    @Override
    public Book removeComment(Long id, Long commentId) {
        Book book = repository.findById(id).orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, id)));
        book.getComments().removeIf(comment -> comment.getId().equals(commentId));

        validate(book);
        return repository.save(book);
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return repository.deleteById(id);
    }

    private void validate(Book book) {
        if (isEmpty(book.getName())) {
            throw new LibraryException("Book name is null or empty!");
        }
    }
}
