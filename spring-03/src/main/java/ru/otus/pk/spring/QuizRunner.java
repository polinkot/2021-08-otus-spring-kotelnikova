package ru.otus.pk.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.service.InOutService;
import ru.otus.pk.spring.service.QuizService;

@Component
public class QuizRunner implements CommandLineRunner {

    private final QuizService service;

    private final InOutService inOutService;

    public QuizRunner(QuizService service, InOutService inOutService) {
        this.service = service;
        this.inOutService = inOutService;
    }

    @Override
    public void run(String... args) {
        try {
            service.startQuiz();
        } catch (AppException ae) {
            inOutService.println("\n" + ae.getMessage());
        }
    }
}