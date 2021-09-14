package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> findAll();
}