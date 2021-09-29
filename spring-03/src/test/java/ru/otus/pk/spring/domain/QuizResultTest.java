package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс CorrectAnswers")
class QuizResultTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        UserInfo userInfo = new UserInfo();
        int correctCount = 3;
        int totalCount = 5;
        boolean passed = false;
        QuizResult quizResult = new QuizResult(userInfo, correctCount, totalCount, passed);

        assertThat(quizResult).hasFieldOrPropertyWithValue("userInfo", userInfo);
        assertThat(quizResult).hasFieldOrPropertyWithValue("correctCount", correctCount);
        assertThat(quizResult).hasFieldOrPropertyWithValue("totalCount", totalCount);
        assertThat(quizResult).hasFieldOrPropertyWithValue("passed", passed);
    }
}