package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.dao.AuthorDao;
import ru.otus.pk.spring.dao.BookDao;
import ru.otus.pk.spring.dao.GenreDao;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.BookDto;
import ru.otus.pk.spring.exception.LibraryException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao dao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

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

//    Вопрос - думала как получить собранную модель книги (с авторами и жанрами) с помощью jdbc.
//    В таком варианте как-то не очень. Много запросов к бд. Хотя можно поторяющихся авторов складывать в Map
//    и в следующий раз не вытаскивать. Но всё равно по одному их вытаскиваем.
//    Или собрать сначала айдишки авторов и одним запросом вытащить всех, а потом по моделям распределить?
//    Но это тоже как-то очень громоздко.
//    Второй вариант был - сделать отдельные запросы с джойнами. Но это совсем плохо - получится
//   на каждую  выборку нужен отдельный запрос.
//    Как лучше делать? Ведь такая задача очень частая. JPA сам вытаскивает ассоциации. А как делать в JDBC?
    @Override
    public List<Book> getWholeBooks(List<Book> books) {
        List<Book> result = new ArrayList<>();

        books.forEach(book -> {
            Author author = authorDao.getById(book.getAuthorId());
            Genre genre = genreDao.getById(book.getGenreId());

            result.add(new BookDto(book.getId(), book.getName(), author, genre));
        });

        return result;
    }

    @Override
    public Number insert(String name, Long authorId, Long genreId) {
        Book book = new Book(null, name, authorId, genreId);
        validate(book);
        return dao.insert(book);
    }

    @Override
    public int update(Long id, String name, Long authorId, Long genreId) {
        Book book = new Book(id, name, authorId, genreId);
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