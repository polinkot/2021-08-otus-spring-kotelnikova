package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Dog;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
