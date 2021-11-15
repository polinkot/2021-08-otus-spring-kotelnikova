package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    long count();

    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Long id, String text, Long bookId);

    void deleteById(Long id);

    List<Comment> findByBookId(Long bookId);
}
