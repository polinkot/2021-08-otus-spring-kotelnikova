package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.dto.BookDto;

import java.util.List;

public interface BookService {

    Long count();

    List<BookDto> getAll();

    BookDto getById(String id);

    Book findById(String id);

    BookDto add(String name,
                String authorId, String authorFirstName, String authorLastName,
                String genreId, String genreName);

    BookDto edit(String id, String name);

    void deleteById(String id);

//    List<Book> findByAuthorId(Long authorId);
//
//    List<Book> findByGenreId(Long genreId);
}
