package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    long count();

    List<Comment> findAll();

    Comment findById(String id);

    Comment add(String text, String bookId);

    Comment edit(String id, String text);

    void deleteById(String id);

    List<Comment> findByBookId(String bookId);

    void deleteByBookId(String bookId);
}
