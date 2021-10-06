package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;

public interface ResultService {
    void print(UserInfo userInfo, QuizResult quizResult, int totalCount);
}