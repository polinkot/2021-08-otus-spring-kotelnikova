package ru.otus.pk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс ResultServiceImpl")
class ResultServiceImplTest {
    private final MessageSourceAccessor messageSourceAccessor = mock(MessageSourceAccessor.class);

    @DisplayName("корректно печатает сообщение о пройденном тесте")
    @Test
    void shouldPrintSuccessCorrectly() {
        int totalCount = 5;
        int correctAnswersCount = 4;
        String message = "Congratulations! You have passed the test!";

        QuizResult quizResult = new QuizResult();
        quizResult.setCount(correctAnswersCount);
        quizResult.setPassed(true);

        given(messageSourceAccessor.getMessage("quiz.success")).willReturn(message);

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ResultServiceImpl service = new ResultServiceImpl(new InOutServiceStreams(System.in, new PrintStream(out), messageSourceAccessor),
                messageSourceAccessor);

        service.print(new UserInfo(), quizResult, totalCount);

        assertThat(out.toString()).contains(message);
    }

    @DisplayName("корректно печатает сообщение о непройденном тесте")
    @Test
    void shouldPrintFailureCorrectly() {
        int totalCount = 5;
        int correctAnswersCount = 2;
        String message = "You haven't passed the test. Try again!";

        QuizResult quizResult = new QuizResult();
        quizResult.setCount(correctAnswersCount);
        quizResult.setPassed(false);

        given(messageSourceAccessor.getMessage("quiz.failure")).willReturn(message);

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ResultServiceImpl service = new ResultServiceImpl(new InOutServiceStreams(System.in, new PrintStream(out), messageSourceAccessor),
                messageSourceAccessor);

        service.print(new UserInfo(), quizResult, totalCount);

        assertThat(out.toString()).contains(message);
    }
}