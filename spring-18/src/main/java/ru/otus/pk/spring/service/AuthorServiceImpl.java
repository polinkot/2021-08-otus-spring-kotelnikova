package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.util.ObjectUtils.isEmpty;
import static ru.otus.pk.spring.resilience.Utils.failureForDemo;

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
        failureForDemo("ru.otus.pk.spring.service.AuthorServiceImpl.findAll");

        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(Long id) {
        failureForDemo("ru.otus.pk.spring.service.AuthorServiceImpl.findById");

        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(AUTHOR_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Author save(Author author) {
        validate(author);
        return repository.save(author);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format(AUTHOR_NOT_FOUND, id), e);
        }
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
