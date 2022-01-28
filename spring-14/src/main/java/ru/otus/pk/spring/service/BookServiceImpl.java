package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Transactional
    @Override
    public Map<String, Book> findBooks(List list) {
        Map<String, Book> books = new HashMap<>();
        new ArrayList<Comment>(list)
                .forEach(comment -> books.put(comment.getBook().getMongoId(), comment.getBook()));

        repository.findByMongoIdIn(books.keySet())
                .forEach(book -> books.put(book.getMongoId(), book));

        return books;
    }
}
