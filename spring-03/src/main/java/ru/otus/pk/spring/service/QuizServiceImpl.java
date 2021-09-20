package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
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
                           @Value("${questions.correct.min}") int correctMin) {
        this.questionService = questionService;
        this.in = new Scanner(inOutService.getIn());
        this.out = inOutService.getOut();
        this.messageSourceAccessor = messageSourceAccessor;
        this.correctMin = correctMin;
    }

    public void interview() {
        Result result = new Result();
        out.println(getMessage("interview.firstname"));
        result.setFirstName(in.nextLine());
        out.println(getMessage("interview.lastname"));
        result.setLastName(in.nextLine());

        out.println(getMessage("interview.questions"));
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
        out.printf(getMessage("interview.result"), result.getFirstName(), result.getLastName(),
                result.getCorrectAnswers(), questions.size());
        out.println(result.getCorrectAnswers() >= correctMin ?
                getMessage("interview.success") :
                getMessage("interview.failure"));
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
            out.println(getMessage("interview.enter-integer"));
            if (in.hasNextInt()) {
                return in.nextInt();
            }

            in.next();
            out.println(getMessage("interview.incorrect-format"));
        }

        throw new AppException(getMessage("interview.error"));
    }
}