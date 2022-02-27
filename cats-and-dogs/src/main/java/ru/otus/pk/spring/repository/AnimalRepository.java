package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
