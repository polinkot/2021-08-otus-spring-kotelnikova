package ru.otus.pk.spring.sevrice;

import ru.otus.pk.spring.domain.Dog;

import java.util.List;

public interface DogService {

    List<Dog> findAll();
}
