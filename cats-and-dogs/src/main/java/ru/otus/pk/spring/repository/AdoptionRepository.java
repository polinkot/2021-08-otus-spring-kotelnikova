package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Adoption;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}
