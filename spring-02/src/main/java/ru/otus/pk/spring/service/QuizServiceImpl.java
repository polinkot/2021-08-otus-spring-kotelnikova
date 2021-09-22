package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.dto.Quiz;
import ru.otus.pk.spring.exception.AppException;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final InOutService ioService;
    private final int correctMin;

    public QuizServiceImpl(QuestionService questionService, InOutService ioService,
                           @Value("${questions.correct.min}") int correctMin) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.correctMin = correctMin;
    }

    public void startQuiz() {
        try {
            List<Question> questions = questionService.findAll();

            Quiz quiz = new Quiz(ioService, questions, correctMin);
            quiz.requestFirstName();
            quiz.requestLastName();

            quiz.askQuestions();

            quiz.printResult();
        } catch (AppException ae) {
            ioService.println("\n" + ae.getMessage());
        }
    }
}