package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.dao.BookDao;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.exception.LibraryException;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao dao;

    @Override
    public List<Book> getAll() {
        return dao.getAll();
    }

    @Override
    public int count() {
        return dao.count();
    }

    @Override
    public Book getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public Long insert(String name, Long authorId, Long genreId) {
        Book book = new Book(null, name, new Author(authorId, null, null), new Genre(genreId, null));
        validate(book);
        return dao.insert(book);
    }

    //Вопрос
    //Сюда лучше передать собранный Book вместо отдельных параметров?
    //А собрать Book в шелле?
    @Override
    public int update(Long id, String name, Long authorId, Long genreId) {
        Book book = new Book(id, name, new Author(authorId, null, null), new Genre(genreId, null));
        ofNullable(id).orElseThrow(() -> new LibraryException("Book id is null!!!"));
        validate(book);
        return dao.update(book);
    }

    @Override
    public int deleteById(Long id) {
        return dao.deleteById(id);
    }

    private void validate(Book book) {
        if (isEmpty(book.getName())) {
            throw new LibraryException("Book name is null or empty!");
        }
    }
}