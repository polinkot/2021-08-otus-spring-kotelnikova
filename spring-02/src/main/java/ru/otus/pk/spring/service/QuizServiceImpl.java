package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.CorrectAnswers;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.UserInfo;
import ru.otus.pk.spring.exception.AppException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final InOutService ioService;
    private final UserService userService;
    private final QuestionViewService questionViewService;
    private final ResultService resultService;

    public void startQuiz() {
        try {
            List<Question> questions = questionService.findAll();

            UserInfo userInfo = userService.requestUserInfo();

            CorrectAnswers correctAnswers = askQuestions(questions);
            resultService.print(userInfo, correctAnswers, questions.size());
        } catch (AppException ae) {
            ioService.println("\n" + ae.getMessage());
        }
    }

    private CorrectAnswers askQuestions(List<Question> questions) {
        CorrectAnswers correctAnswers = new CorrectAnswers();

        ioService.println("Please, answer the questions: ");
        questions.forEach(question -> {
            ioService.println(questionViewService.asString(question));

            int correctAnswer = question.getCorrectAnswer()
                    .orElseThrow(() -> new AppException("No correct answer for question: " + this))
                    .getNumber();
            int answer = ioService.readInt();
            if (answer == correctAnswer) {
                correctAnswers.increaseCount();
            }
        });

        return correctAnswers;
    }
}