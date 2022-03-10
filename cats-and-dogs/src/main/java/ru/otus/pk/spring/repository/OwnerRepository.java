package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
