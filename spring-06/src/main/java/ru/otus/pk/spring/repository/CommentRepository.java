package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Long count();

    Optional<Comment> findById(Long id);

    List<Comment> findAll();

    Comment save(Comment comment);

    int deleteById(Long id);

    List<Comment> findByBookId(Long bookId);
}
