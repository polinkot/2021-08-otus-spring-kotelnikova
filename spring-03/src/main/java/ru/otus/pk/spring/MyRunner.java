package ru.otus.pk.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.service.InOutService;
import ru.otus.pk.spring.service.QuizService;

@Component
public class MyRunner implements CommandLineRunner {

    private final QuizService service;

    private final InOutService inOutService;

    public MyRunner(QuizService service, InOutService inOutService) {
        this.service = service;
        this.inOutService = inOutService;
    }

    @Override
    public void run(String... args) {
        try {
            service.interview();
        } catch (AppException ae) {
            inOutService.getOut().println("\n" + ae.getMessage());
        }
    }
}