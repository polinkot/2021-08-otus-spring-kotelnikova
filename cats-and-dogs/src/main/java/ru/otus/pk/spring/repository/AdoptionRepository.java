package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.pk.spring.domain.Adoption;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    @EntityGraph(value = "Adoption.Animal.Owner")
    @Override
    List<Adoption> findAll();

    @EntityGraph(value = "Adoption.Animal.Owner")
    List<Adoption> findByOwnerId(Long ownerId);

    @EntityGraph(value = "Adoption.Animal.Owner")
    Adoption findFirstByAnimalId(Long animalId);
}
