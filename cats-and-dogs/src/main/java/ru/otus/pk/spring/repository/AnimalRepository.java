package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Animal;
import ru.otus.pk.spring.domain.Animal.AnimalStatus;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByStatus(AnimalStatus status);
}
