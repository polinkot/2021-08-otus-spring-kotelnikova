package ru.otus.pk.spring.service;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.model.Book;

import java.util.List;

public interface BookService {

    long count();

    List<Book> findAll();

    Book findById(Long id);

    Book save(Long id, String name, Long authorId, Long genreId);

    @Transactional
    Book addComment(Long id, String comment);

    @Transactional
    Book removeComment(Long id, Long commentId);

    int deleteById(Long id);
}
