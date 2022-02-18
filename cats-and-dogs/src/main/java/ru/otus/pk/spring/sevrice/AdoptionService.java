package ru.otus.pk.spring.sevrice;

import ru.otus.pk.spring.domain.Adoption;

import java.util.List;

public interface AdoptionService {

    List<Adoption> findAll();
}
