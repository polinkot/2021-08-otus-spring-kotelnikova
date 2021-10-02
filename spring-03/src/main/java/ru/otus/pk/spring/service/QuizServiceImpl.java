package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.config.QuizConfig;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;
import ru.otus.pk.spring.exception.AppException;

import java.util.List;

import static java.lang.String.format;
import static ru.otus.pk.spring.config.MessageSourceConfig.*;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final InOutService ioService;
    private final UserService userService;
    private final QuestionViewService questionViewService;
    private final ResultService resultService;
    private final MessageService messageService;
    private final QuizConfig quizConfig;

    public void startQuiz() {
        try {
            UserInfo userInfo = userService.requestUserInfo();

            List<Question> questions = questionService.findAll();

            int correctCount = askQuestions(questions);

            boolean isPassed = correctCount >= quizConfig.getPassGrade();
            QuizResult quizResult = new QuizResult(userInfo, correctCount, questions.size(), isPassed);

            resultService.print(quizResult);
        } catch (AppException ae) {
            ioService.println("\n" + ae.getMessage());
        }
    }

    private String getMessage(String key) {
        return messageService.getMessage(key);
    }

    private int askQuestions(List<Question> questions) {
        int result = 0;

        ioService.println(getMessage(QUIZ_QUESTIONS));
        for (Question question : questions) {
            ioService.println(questionViewService.asString(question));

            int correctAnswer = question.getCorrectAnswer()
                    .orElseThrow(() -> new AppException(format("Error!!! No correct answer for question: %s", question.getValue())))
                    .getNumber();
            int answer = ioService.readInt(getMessage(QUIZ_ENTER_INTEGER), getMessage(QUIZ_INCORRECT_FORMAT),
                    quizConfig.getAttemptsCount());
            if (answer == correctAnswer) {
                result++;
            }
        }

        return result;
    }
}