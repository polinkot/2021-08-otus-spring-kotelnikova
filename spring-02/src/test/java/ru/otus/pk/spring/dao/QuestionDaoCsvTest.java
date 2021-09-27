package ru.otus.pk.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.pk.spring.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDaoCsv")
class QuestionDaoCsvTest {

    private final QuestionDao dao = new QuestionDaoCsv("/csv/questions-test.csv");

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
        List<Question> all = dao.findAll();

        int expectedSize = 3;
        assertThat(all).hasSize(expectedSize)
                .hasOnlyElementsOfType(Question.class);
    }
}