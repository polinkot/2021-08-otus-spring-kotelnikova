package ru.otus.pk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.dao.QuestionDao;
import ru.otus.pk.spring.domain.Question;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс QuestionServiceImpl")
@SpringBootTest(classes = QuestionServiceImpl.class)
class QuestionServiceImplTest {

    @MockBean
    private QuestionDao dao;

    @Autowired
    private QuestionServiceImpl service;

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
        Question questions1 = new Question("questions1");
        Question questions2 = new Question("questions2");
        List<Question> questions = asList(questions1, questions2);

        given(dao.findAll()).willReturn(questions);

        assertThat(service.findAll())
                .hasSize(2)
                .containsExactly(questions1, questions2);
    }
}