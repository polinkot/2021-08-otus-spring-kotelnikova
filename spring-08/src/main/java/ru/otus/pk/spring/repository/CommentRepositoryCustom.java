package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.dto.CommentDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommentRepositoryCustom {
    List<CommentDto> getAll();

    Optional<CommentDto> getById(String commentId);

    List<CommentDto> getComments(Set<String> commentIds);
}
