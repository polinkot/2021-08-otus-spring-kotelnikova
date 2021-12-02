package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    long count();

    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);

    List<Comment> findByBookId(Long bookId);
}
