package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
