package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.CorrectAnswers;
import ru.otus.pk.spring.domain.PassGrade;
import ru.otus.pk.spring.domain.UserInfo;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final InOutService ioService;
    private final PassGrade passGrade;

    public void print(UserInfo userInfo, CorrectAnswers correctAnswers, int totalCount) {
        ioService.println(format("\n%s %s, you have answered correctly %d questions of %d.",
                userInfo.getFirstName(), userInfo.getLastName(),
                correctAnswers.getCount(), totalCount));
        ioService.println(correctAnswers.getCount() >= passGrade.getValue() ?
                "Congratulations! You have passed the test!" :
                "You haven't passed the test. Try again!");
    }
}