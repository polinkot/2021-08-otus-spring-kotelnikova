package ru.otus.pk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.pk.spring.dao.QuestionDao;
import ru.otus.pk.spring.domain.Question;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс QuestionServiceImpl")
class QuestionServiceImplTest {

    private final QuestionDao dao = mock(QuestionDao.class);

    private final QuestionServiceImpl service = new QuestionServiceImpl(dao);

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