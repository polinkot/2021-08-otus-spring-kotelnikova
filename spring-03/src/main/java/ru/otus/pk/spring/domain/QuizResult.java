package ru.otus.pk.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class QuizResult {
    private final UserInfo userInfo;
    private final int correctCount;
    private final int totalCount;
    private final boolean passed;
}