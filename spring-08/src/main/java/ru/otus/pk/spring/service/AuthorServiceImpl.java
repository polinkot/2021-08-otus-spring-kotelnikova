package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    public static final String AUTHOR_NOT_FOUND = "Author not found!!! id = %s";

    private final BookRepository bookRepository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    @Override
    public Integer count() {
        return bookRepository.findAllAuthors().size();

    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return bookRepository.findAllAuthors();
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(String id) {
        Book book = bookRepository.findFirstByAuthorId(id)
                .orElseThrow(() -> new LibraryException(format(AUTHOR_NOT_FOUND, id)));
        return book.getAuthor();
    }

    @Transactional
    @Override
    public void save(String id, String firstName, String lastName) {
        List<Book> books = bookRepository.findByAuthorId(id);
        books.forEach(book -> {
            Author author = book.getAuthor();
            author.setFirstName(firstName);
            author.setLastName(lastName);
            validate(author);
        });
        bookRepository.saveAll(books);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        List<Book> books = bookRepository.findByAuthorId(id);
        books.forEach(book -> commentService.deleteByBookId(book.getId()));
        bookRepository.deleteAll(books);
    }

    private void validate(Author author) {
        List<String> errors = new ArrayList<>();

        if (isEmpty(author.getFirstName())) {
            errors.add("Author first name is null or empty!");
        }

        if (isEmpty(author.getLastName())) {
            errors.add("Author last name is null or empty!");
        }

        if (!isEmpty(errors)) {
            throw new LibraryException(join("\n", errors));
        }
    }
}
