package ru.otus.pk.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.pk.spring.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDaoCsv")
class QuestionDaoCsvTest {

    public static final int EXPECTED_SIZE = 3;

    private final QuestionDao dao = new QuestionDaoCsv("/csv/questions.csv");

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
        List<Question> all = dao.findAll();
        assertThat(all).hasSize(EXPECTED_SIZE)
                .hasOnlyElementsOfType(Question.class);
    }
}