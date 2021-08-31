package ru.otus.pk.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.Quiz;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Вопрос - как назвать тест?
//QuestionServiceImplTest или QuestionServiceTest?
class QuestionServiceImplTest {

    private QuestionService service;

    @BeforeEach
    void init() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        service = context.getBean(QuestionService.class);
    }

    @DisplayName("правильное количество вопросов")
    @Test
    void shouldReturnCorrectNumberOfQuestions() {
        try {
            List<Question> all = service.findAll();
            assertEquals(5, all.size());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("правильное количество вопросов в опросе")
    @Test
    void shouldReturnQuizWithCorrectNumberOfQuestions() {
        try {
            Quiz quiz = service.findQuiz();
            assertEquals(5, quiz.getQuestions().size());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}