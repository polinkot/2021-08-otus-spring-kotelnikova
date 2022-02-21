package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Adoption;

import java.util.List;

public interface AdoptionService {

    List<Adoption> findAll();

    Adoption findById(Long id);

    Adoption save(Adoption adoption);

    void deleteById(Long id);
}
