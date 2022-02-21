package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Dog;

import java.util.List;

public interface DogService {

    List<Dog> findAll();

    Dog findById(Long id);

    Dog save(Dog Dog);

    void deleteById(Long id);
}
