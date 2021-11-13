package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    public static final String AUTHOR_NOT_FOUND = "Author not found!!! id = %s";

    private final AuthorRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Long count() {
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

    @Transactional(readOnly = true)
    @Override
    public Author createNew(String firstName, String lastName) {
        Author author = new Author(null, firstName, lastName);
        validate(author);
        return author;
    }

    @Transactional
    @Override
    public Author save(Long id, String firstName, String lastName) {
        Author author = id != null ? findById(id) : new Author();

        author.setFirstName(firstName);
        author.setLastName(lastName);

        validate(author);
        return repository.save(author);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
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
