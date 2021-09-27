package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.QuizResult;
import ru.otus.pk.spring.domain.UserInfo;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.exception.WrongFormatException;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final InOutService ioService;
    private final UserService userService;
    private final QuestionViewService questionViewService;
    private final ResultService resultService;
    private final int attemptsCount;
    private final int passGrade;

    public QuizServiceImpl(QuestionService questionService, InOutService ioService, UserService userService,
                           QuestionViewService questionViewService, ResultService resultService,
                           @Value("${questions.attempts.count}") int attemptsCount,
                           @Value("${questions.pass.grade}") int passGrade) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.userService = userService;
        this.questionViewService = questionViewService;
        this.resultService = resultService;
        this.attemptsCount = attemptsCount;
        this.passGrade = passGrade;
    }

    public void startQuiz() {
        try {
            List<Question> questions = questionService.findAll();

            UserInfo userInfo = userService.requestUserInfo();

            // а здесь тоже лучше вынести в отдельный сервис?
            // вместе с методом askQuestions
            QuizResult quizResult = askQuestions(questions);
            quizResult.setPassed(quizResult.getCount() >= passGrade);

            resultService.print(userInfo, quizResult, questions.size());
        } catch (AppException | WrongFormatException ae) {
            ioService.println("\n" + ae.getMessage());
        }
    }

    private QuizResult askQuestions(List<Question> questions) {
        QuizResult quizResult = new QuizResult();

        ioService.println("Please, answer the questions: ");
        questions.forEach(question -> {
            ioService.println(questionViewService.asString(question));

            int correctAnswer = question.getCorrectAnswer()
                    .orElseThrow(() -> new AppException("No correct answer for question: " + this))
                    .getNumber();
            int answer = ioService.readInt("Please, enter an integer number: ", "Incorrect format.", attemptsCount);
            if (answer == correctAnswer) {
                quizResult.increaseCount();
            }
        });

        return quizResult;
    }
}