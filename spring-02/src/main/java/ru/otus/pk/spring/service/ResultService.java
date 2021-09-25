package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.CorrectAnswers;
import ru.otus.pk.spring.domain.UserInfo;

public interface ResultService {
    void print(UserInfo userInfo, CorrectAnswers correctAnswers, int totalCount);
}