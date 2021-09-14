package ru.otus.pk.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.pk.spring.dao.QuestionDao;
import ru.otus.pk.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionServiceImplTest {

    private final QuestionDao daoMock = mock(QuestionDao.class);

    private final InOutService inOutMock = mock(InOutService.class);

    @BeforeEach
    void setUp() {
//        when(daoMock.findAll()).thenReturn(asList(
//                new Question("q1", new ArrayList<>()),
//                new Question("q2", new ArrayList<>())
//        ));
    }

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
//        QuestionServiceImpl service = new QuestionServiceImpl(daoMock, inOutMock);
//        List<Question> all = service.findAll();
//        assertEquals(2, all.size());
    }
}