package ru.otus.pk.spring.dao;

import ru.otus.pk.spring.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();
}