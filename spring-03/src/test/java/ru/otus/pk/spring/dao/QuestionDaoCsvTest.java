package ru.otus.pk.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.service.CsvSourceProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс QuestionDaoCsv")
@SpringBootTest(classes = QuestionDaoCsv.class)
class QuestionDaoCsvTest {

    @MockBean
    private CsvSourceProvider csvSourceProvider;

    @Autowired
    private QuestionDao dao;

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
        String csv = "/csv/questions-test.csv";
        given(csvSourceProvider.getCsvSource()).willReturn(csv);

        int expectedSize = 3;
        List<Question> all = dao.findAll();
        assertThat(all).hasSize(expectedSize)
                .hasOnlyElementsOfType(Question.class);
    }
}