package ru.otus.pk.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.service.CsvSourceProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс QuestionDaoCsv")
class QuestionDaoCsvTest {

    private final CsvSourceProvider csvSourceProvider = mock(CsvSourceProvider.class);

    private final QuestionDao dao = new QuestionDaoCsv(csvSourceProvider);

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
        String csv = "/csv/questions.csv";
        given(csvSourceProvider.getCsvSource()).willReturn(csv);

        int expectedSize = 3;
        List<Question> all = dao.findAll();
        assertThat(all).hasSize(expectedSize)
                .hasOnlyElementsOfType(Question.class);
    }
}