package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.dto.CommentDto;

import java.util.List;

public interface CommentService {

    long count();

    List<CommentDto> getAll();

    Comment findById(String id);

    CommentDto getById(String id);

    CommentDto add(String text, String bookId);

    CommentDto edit(String id, String text);

    void deleteById(String id);

    void deleteAll(List<Comment> comments);

    List<CommentDto> findByBookId(String bookId);
}
