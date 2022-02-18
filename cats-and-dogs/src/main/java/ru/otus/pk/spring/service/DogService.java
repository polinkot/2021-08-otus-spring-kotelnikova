package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Dog;

import java.util.List;

public interface DogService {

    List<Dog> findAll();
}
