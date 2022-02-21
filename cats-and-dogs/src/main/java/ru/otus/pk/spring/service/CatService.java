package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Cat;

import java.util.List;

public interface CatService {

//    Long count();

    List<Cat> findAll();

//    Cat findById(Long id);
//
//    Cat save(Cat cat);
//
//    void deleteById(Long id);
}
