package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.config.QuizConfig;
import ru.otus.pk.spring.domain.CorrectAnswers;
import ru.otus.pk.spring.domain.UserInfo;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final InOutService ioService;
    private final QuizConfig quizConfig;
    private final MessageSourceAccessor messageSourceAccessor;

    public void print(UserInfo userInfo, CorrectAnswers correctAnswers, int totalCount) {
        ioService.println("\n" + messageSourceAccessor.getMessage("quiz.result",
                new Object[]{userInfo.getFirstName(), userInfo.getLastName(), correctAnswers.getCount(), totalCount}));
        ioService.println(correctAnswers.getCount() >= quizConfig.getPassGrade()
                ? getMessage("quiz.success")
                : getMessage("quiz.failure"));
    }

    private String getMessage(String key) {
        return messageSourceAccessor.getMessage(key);
    }
}