package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;

import static ru.otus.pk.spring.config.MessageSourceConfig.*;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final LocalizedIOService localizedIOService;

    @Override
    public void print(QuizResult quizResult) {
        UserInfo userInfo = quizResult.getUserInfo();

        localizedIOService.printlnLocalized(QUIZ_RESULT,
                userInfo.getFirstName(), userInfo.getLastName(),
                quizResult.getCorrectCount(), quizResult.getTotalCount());

        localizedIOService.printlnLocalized(quizResult.isPassed() ? QUIZ_SUCCESS : QUIZ_FAILURE);
    }
}