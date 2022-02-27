package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Animal;

import java.util.List;

public interface AnimalService {

    List<Animal> findAll();

    Animal findById(Long id);

    Animal save(Animal animal);

    void deleteById(Long id);
}
