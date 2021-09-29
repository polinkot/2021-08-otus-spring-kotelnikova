package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.config.QuizConfig;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;
import ru.otus.pk.spring.exception.AppException;

import java.util.List;

import static ru.otus.pk.spring.config.MessageSourceConfig.*;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final InOutService ioService;
    private final UserService userService;
    private final QuestionViewService questionViewService;
    private final ResultService resultService;
    private final MessageSourceAccessor messageSourceAccessor;
    private final QuizConfig quizConfig;

    public void startQuiz() {
        try {
            List<Question> questions = questionService.findAll();

            UserInfo userInfo = userService.requestUserInfo();

            QuizResult quizResult = askQuestions(questions);
            quizResult.setPassed(quizResult.getCount() >= quizConfig.getPassGrade());
            resultService.print(userInfo, quizResult, questions.size());
        } catch (AppException ae) {
            ioService.println("\n" + ae.getMessage());
        }
    }

    private String getMessage(String key) {
        return messageSourceAccessor.getMessage(key);
    }

    private QuizResult askQuestions(List<Question> questions) {
        QuizResult quizResult = new QuizResult();

        ioService.println(getMessage(QUIZ_QUESTIONS));
        questions.forEach(question -> {
            ioService.println(questionViewService.asString(question));

            int correctAnswer = question.getCorrectAnswer()
                    .orElseThrow(() -> new AppException(getMessage(QUIZ_ERROR_NO_CORRECT_ANSWER) + question.getValue()))
                    .getNumber();
            int answer = ioService.readInt(getMessage(QUIZ_ENTER_INTEGER), getMessage(QUIZ_INCORRECT_FORMAT),
                    quizConfig.getAttemptsCount());
            if (answer == correctAnswer) {
                quizResult.increaseCount();
            }
        });

        return quizResult;
    }
}