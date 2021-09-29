package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;

import static ru.otus.pk.spring.config.MessageSourceConfig.*;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final InOutService ioService;
    private final MessageSourceAccessor messageSourceAccessor;

    @Override
    public void print(QuizResult quizResult) {
        UserInfo userInfo = quizResult.getUserInfo();
        ioService.println("\n" + messageSourceAccessor.getMessage(QUIZ_RESULT,
                new Object[]{userInfo.getFirstName(), userInfo.getLastName(),
                        quizResult.getCorrectCount(), quizResult.getTotalCount()}));
        ioService.println(quizResult.isPassed()
                ? getMessage(QUIZ_SUCCESS)
                : getMessage(QUIZ_FAILURE));
    }

    private String getMessage(String key) {
        return messageSourceAccessor.getMessage(key);
    }
}