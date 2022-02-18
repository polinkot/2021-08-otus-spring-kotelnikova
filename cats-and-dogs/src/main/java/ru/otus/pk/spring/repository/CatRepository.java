package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Cat;

public interface CatRepository extends JpaRepository<Cat, Long> {
}
