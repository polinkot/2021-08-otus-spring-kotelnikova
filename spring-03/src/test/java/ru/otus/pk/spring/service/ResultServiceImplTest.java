package ru.otus.pk.spring.service;

import org.junit.jupiter.api.BeforeEach;
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

    private ByteArrayOutputStream out;
    private ResultServiceImpl service;

    @BeforeEach
    void setUp() {
        this.out = new ByteArrayOutputStream(1024);
        this.service = new ResultServiceImpl(new InOutServiceStreams(System.in, new PrintStream(out)),
                messageSourceAccessor);
    }

    @DisplayName("корректно печатает сообщение о пройденном тесте")
    @Test
    void shouldPrintSuccessCorrectly() {
        int correctCount = 4;
        int totalCount = 5;
        boolean passed = true;
        String message = "Congratulations! You have passed the test!";

        QuizResult quizResult = new QuizResult(new UserInfo(), correctCount, totalCount, passed);

        given(messageSourceAccessor.getMessage("quiz.success")).willReturn(message);

        service.print(quizResult);

        assertThat(out.toString()).contains(message);
    }

    @DisplayName("корректно печатает сообщение о непройденном тесте")
    @Test
    void shouldPrintFailureCorrectly() {
        int correctCount = 2;
        int totalCount = 5;
        boolean passed = false;
        String message = "You haven't passed the test. Try again!";

        QuizResult quizResult = new QuizResult(new UserInfo(), correctCount, totalCount, passed);

        given(messageSourceAccessor.getMessage("quiz.failure")).willReturn(message);

        service.print(quizResult);

        assertThat(out.toString()).contains(message);
    }
}