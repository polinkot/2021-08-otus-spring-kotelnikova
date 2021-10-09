package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Question;

public interface QuestionViewService {
    String asString(Question question);
}