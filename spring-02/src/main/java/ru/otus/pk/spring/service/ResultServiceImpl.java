package ru.otus.pk.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;

@Service
public class ResultServiceImpl implements ResultService {

    private final InOutService ioService;

    public ResultServiceImpl(InOutService ioService) {
        this.ioService = ioService;
    }

    public void print(UserInfo userInfo, QuizResult quizResult, int totalCount) {
        ioService.printf("\n%s %s, you have answered correctly %d questions of %d.%n",
                userInfo.getFirstName(), userInfo.getLastName(),
                quizResult.getCount(), totalCount);
        ioService.println(quizResult.isPassed() ?
                "Congratulations! You have passed the test!" :
                "You haven't passed the test. Try again!");
    }
}