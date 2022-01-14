package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
