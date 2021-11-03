package ru.otus.pk.spring.service;

import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Comment;

import java.util.List;

public interface BookService {

    Long count();

    List<Book> findAll();

    Book findById(Long id);

    Book createNew(String bookName,
                   Long authorId, String authorFirstName, String authorLastName,
                   Long genreId, String genreName);

    Book save(Long id, String name,
              Long authorId, String authorFirstName, String authorLastName,
              Long genreId, String genreName);

    int deleteById(Long id);

    List<Comment> findComments(Long id);
}
