package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Question")
class QuestionTest {
    private Question question;
    private Answer answer1;
    private Answer answer2;
    private List<Answer> answers;

    @BeforeEach
    void setUp() {
        question = new Question("question1");
        answer1 = new Answer("answer1", true);
        answer2 = new Answer("answer2", false);
        answers = asList(answer1, answer2);
    }

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Question question = new Question("question2");

        assertThat(question)
                .hasFieldOrPropertyWithValue("value", "question2");
    }

    @DisplayName("корректно устанавливает ответы")
    @Test
    void shouldSetCorrectAnswers() {
        question.setAnswers(answers);

        int expected = 2;
        assertThat(question.getAnswers())
                .hasSize(expected)
                .containsExactly(answer1, answer2);
    }

    @DisplayName("корректно определяет правильный ответ")
    @Test
    void shouldFindCorrectAnswer() {
        question.setAnswers(answers);
        Answer correctAnswer = question.getCorrectAnswer().orElse(null);

        assertThat(correctAnswer)
                .isNotNull()
                .hasFieldOrPropertyWithValue("value", "answer1")
                .hasFieldOrPropertyWithValue("correct", true);
    }
}