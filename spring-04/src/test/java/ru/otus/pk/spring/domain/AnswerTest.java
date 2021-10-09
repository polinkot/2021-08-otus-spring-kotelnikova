package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Answer")
class AnswerTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Answer answer = new Answer("answer2", true);

        assertThat(answer)
                .hasFieldOrPropertyWithValue("value", "answer2")
                .hasFieldOrPropertyWithValue("correct", true);
    }

    @DisplayName("корректно устанавливает номер")
    @Test
    void shouldSetCorrectNumber() {
        Answer answer = new Answer("answer1", true);
        answer.setNumber(3);

        assertThat(answer.getNumber()).isEqualTo(3);
    }
}