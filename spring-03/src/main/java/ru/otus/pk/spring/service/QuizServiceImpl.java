package ru.otus.pk.spring.service;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.config.QuizConfig;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.Result;
import ru.otus.pk.spring.exception.AppException;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final Scanner in;
    private final PrintStream out;
    private final MessageSourceAccessor messageSourceAccessor;

    private final int correctMin;

    public QuizServiceImpl(QuestionService questionService, InOutService inOutService,
                           MessageSourceAccessor messageSourceAccessor,
                           QuizConfig quizConfig) {
        this.questionService = questionService;
        this.in = new Scanner(inOutService.getIn());
        this.out = inOutService.getOut();
        this.messageSourceAccessor = messageSourceAccessor;
        this.correctMin = quizConfig.getCorrect().getMin();
    }

    public void startQuiz() {
        Result result = new Result();
        out.println(getMessage("quiz.firstname"));
        result.setFirstName(in.nextLine());
        out.println(getMessage("quiz.lastname"));
        result.setLastName(in.nextLine());

        out.println(getMessage("quiz.questions"));
        List<Question> questions = questionService.findAll();
        questions.forEach(question -> {
            out.println(question.asString());

            int correctAnswer = question.getCorrectAnswer().getNumber();
            int answer = readAnswer();
            if (answer == correctAnswer) {
                result.increaseCorrectAnswers();
            }
        });

        out.println();
        out.printf(getMessage("quiz.result"), result.getFirstName(), result.getLastName(),
                result.getCorrectAnswers(), questions.size());
        out.println(result.getCorrectAnswers() >= correctMin ?
                getMessage("quiz.success") :
                getMessage("quiz.failure"));
        out.println();
    }

    public void printQuestions() {
        List<Question> questions = questionService.findAll();
        questions.forEach(question -> out.println(question.asString()));
    }

    private String getMessage(String key) {
        return messageSourceAccessor.getMessage(key);
    }

    private int readAnswer() {
        for (int i = 0; i < 3; i++) {
            out.println(getMessage("quiz.enter-integer"));
            if (in.hasNextInt()) {
                return in.nextInt();
            }

            in.next();
            out.println(getMessage("quiz.incorrect-format"));
        }

        throw new AppException(getMessage("quiz.error"));
    }
}