package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.pk.spring.dto.Quiz;
import ru.otus.pk.spring.service.InOutService;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@DisplayName("Класс Quiz")
class QuizTest {

    private final InOutService ioService = mock(InOutService.class);

    public static final int CORRECT_MIN_STUB = 2;

    @DisplayName("корректно устанавливает имя")
    @Test
    void shouldSetCorrectFirstName() {
        String ivan = "Ivan";

        given(ioService.nextLine()).willReturn(ivan);

        Quiz quiz = new Quiz(ioService, emptyList(), CORRECT_MIN_STUB);
        quiz.requestFirstName();

        assertThat(quiz.getFirstName()).isEqualTo(ivan);
    }

    @DisplayName("корректно устанавливает фамилию")
    @Test
    void shouldSetCorrectLastName() {
        String petrov = "Petrov";

        given(ioService.nextLine()).willReturn(petrov);

        Quiz quiz = new Quiz(ioService, emptyList(), CORRECT_MIN_STUB);
        quiz.requestLastName();

        assertThat(quiz.getLastName()).isEqualTo(petrov);
    }

    @DisplayName("корректно считает правильные ответы")
    @Test
    void shouldCalcCorrectAnswersCorrectly() {
        Question question1 = new Question("question1");
        question1.setAnswers(asList(new Answer("answer1", true), new Answer("answer2", false)));

        Question question2 = new Question("question2");
        question2.setAnswers(asList(new Answer("answer21", true), new Answer("answer22", false)));

        int correctAnswerNumber = 1;
        int expectedCorrectAnswersCount = 2;

        given(ioService.hasNextInt()).willReturn(true);
        given(ioService.nextInt()).willReturn(correctAnswerNumber);

        Quiz quiz = new Quiz(ioService, asList(question1, question2), CORRECT_MIN_STUB);
        quiz.askQuestions();

        assertThat(quiz.getCorrectAnswers()).isEqualTo(expectedCorrectAnswersCount);
    }
}