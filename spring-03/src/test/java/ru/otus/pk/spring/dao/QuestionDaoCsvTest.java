package ru.otus.pk.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.pk.spring.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс QuestionDaoCsv")
class QuestionDaoCsvTest {

    private final MessageSourceAccessor messageSourceAccessor = mock(MessageSourceAccessor.class);

    private final QuestionDao dao = new QuestionDaoCsv(messageSourceAccessor);

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
        given(messageSourceAccessor.getMessage("quiz.csv")).willReturn("/csv/questions.csv");

        List<Question> all = dao.findAll();
        assertThat(all).hasSize(3)
                .hasOnlyElementsOfType(Question.class);
    }
}