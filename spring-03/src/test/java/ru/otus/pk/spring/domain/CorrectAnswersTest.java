package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс CorrectAnswers")
class CorrectAnswersTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        CorrectAnswers correctAnswers = new CorrectAnswers();

        assertThat(correctAnswers).hasFieldOrPropertyWithValue("count", 0);
    }

    @DisplayName("корректно увеличивает количество")
    @Test
    void shouldSetCountCorrectly() {
        CorrectAnswers correctAnswers = new CorrectAnswers();
        correctAnswers.increaseCount();

        assertThat(correctAnswers.getCount()).isEqualTo(1);
    }
}