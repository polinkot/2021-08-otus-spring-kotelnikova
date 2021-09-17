package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.Result;
import ru.otus.pk.spring.exception.AppException;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

@Service
public class QuizServiceImpl implements QuizService {

    @Value("${questions.correct.min}")
    private int correctMin;

    private final QuestionService questionService;
    private final Scanner in;
    private final PrintStream out;

    public QuizServiceImpl(QuestionService questionService, InOutService inOutService) {
        this.questionService = questionService;
        this.in = new Scanner(inOutService.getIn());
        this.out = inOutService.getOut();
    }

    public void interview() {
        Result result = new Result();
        out.println("Please, input your first name: ");
        result.setFirstName(in.nextLine());
        out.println("Please, input your last name: ");
        result.setLastName(in.nextLine());

        out.println("Please, answer the questions: ");
        List<Question> questions = questionService.findAll();
        questions.forEach(question -> {
            out.println(question.asString());

            int correctAnswer = question.getCorrectAnswer().getNumber();
            int answer = readAnswer();
            if (answer == correctAnswer) {
                result.increaseCorrectAnswers();
            }
        });

        out.printf("\nYou have answered correctly %d questions of %d.%n", result.getCorrectAnswers(), questions.size());
        out.println(result.getCorrectAnswers() >= correctMin ?
                "Congratulations! You have passed the test!" :
                "You haven't passed the test. Try again!");
    }

    public void printQuestions() {
        List<Question> questions = questionService.findAll();
        questions.forEach(question -> out.println(question.asString()));
    }

    private int readAnswer() {
        for (int i = 0; i < 3; i++) {
            out.println("Please enter an integer number: ");
            if (in.hasNextInt()) {
                return in.nextInt();
            }

            in.next();
            out.println("Incorrect format.");
        }

        throw new AppException("Error!!! Incorrect answer format!!!");
    }
}