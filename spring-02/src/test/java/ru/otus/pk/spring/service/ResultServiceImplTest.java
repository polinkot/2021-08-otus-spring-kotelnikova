package ru.otus.pk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.pk.spring.domain.CorrectAnswers;
import ru.otus.pk.spring.domain.PassGrade;
import ru.otus.pk.spring.domain.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс ResultServiceImpl")
class ResultServiceImplTest {
    private final PassGrade passGrade = mock(PassGrade.class);

    @DisplayName("корректно печатает сообщение о пройденном тесте")
    @Test
    void shouldPrintSuccessCorrectly() {
        int totalCount = 5;
        int passGradeMin = 4;
        int correctAnswersCount = 4;
        String message = "Congratulations! You have passed the test!";

        CorrectAnswers correctAnswers = new CorrectAnswers();
        correctAnswers.setCount(correctAnswersCount);

        given(passGrade.getValue()).willReturn(passGradeMin);

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ResultServiceImpl service = new ResultServiceImpl(new InOutServiceStreams(System.in, new PrintStream(out)), passGrade);

        service.print(new UserInfo(), correctAnswers, totalCount);
        String result = out.toString();

        assertThat(result).contains(message);
    }

    @DisplayName("корректно печатает сообщение о непройденном тесте")
    @Test
    void shouldPrintFailureCorrectly() {
        int totalCount = 5;
        int passGradeMin = 4;
        int correctAnswersCount = 2;
        String message = "You haven't passed the test. Try again!";

        CorrectAnswers correctAnswers = new CorrectAnswers();
        correctAnswers.setCount(correctAnswersCount);

        given(passGrade.getValue()).willReturn(passGradeMin);

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ResultServiceImpl service = new ResultServiceImpl(new InOutServiceStreams(System.in, new PrintStream(out)), passGrade);

        service.print(new UserInfo(), correctAnswers, totalCount);

        assertThat(out.toString()).contains(message);
    }
}