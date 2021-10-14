package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.dao.AuthorDao;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.exception.LibraryException;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }

    @Override
    public int count() {
        return dao.count();
    }

    @Override
    public Author getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public Number insert(String firstName, String lastName) {
        Author author = new Author(null, firstName, lastName);
        validate(author);
        return dao.insert(author);
    }

    @Override
    public int update(Long id, String firstName, String lastName) {
        Author author = new Author(id, firstName, lastName);
        ofNullable(id).orElseThrow(() -> new LibraryException("Author id is null!!!"));
        validate(author);
        return dao.update(author);
    }

    @Override
    public int deleteById(Long id) {
        return dao.deleteById(id);
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
