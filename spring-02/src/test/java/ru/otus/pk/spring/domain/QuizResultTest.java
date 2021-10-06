package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс CorrectAnswers")
class QuizResultTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        QuizResult quizResult = new QuizResult();

        assertThat(quizResult).hasFieldOrPropertyWithValue("count", 0);
    }

    @DisplayName("корректно увеличивает количество")
    @Test
    void shouldSetCountCorrectly() {
        QuizResult quizResult = new QuizResult();
        quizResult.increaseCount();

        assertThat(quizResult.getCount()).isEqualTo(1);
    }
}