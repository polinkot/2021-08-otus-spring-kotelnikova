package ru.otus.pk.spring.repository;

import ru.otus.pk.spring.dto.AuthorDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepositoryCustom {
    List<AuthorDto> getAll();

    Optional<AuthorDto> getById(String authorId);

    List<AuthorDto> getAuthors(Set<String> authorIds);
}
