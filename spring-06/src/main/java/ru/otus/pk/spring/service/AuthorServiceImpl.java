package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    public static final String AUTHOR_NOT_FOUND = "Author not found!!! id = %s";

    private final AuthorRepository repository;

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(AUTHOR_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Author save(Long id, String firstName, String lastName, Set<Book> books) {
        Author author = id != null ?
                repository.findById(id).orElseThrow(() -> new LibraryException(format(AUTHOR_NOT_FOUND, id))) :
                new Author();

        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.addBooks(books);

        validate(author);
        return repository.save(author);
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return repository.deleteById(id);
    }

    private void validate(Author author) {
        if (isEmpty(author.getFirstName())) {
            throw new LibraryException("Author first name is null or empty!");
        }

        if (isEmpty(author.getLastName())) {
            throw new LibraryException("Author last name is null or empty!");
        }
    }
}
