package ru.otus.pk.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.pk.spring.domain.Quiz;
import ru.otus.pk.spring.service.QuestionService;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            printQuiz();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printQuiz() throws IOException, URISyntaxException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        Quiz quiz = service.findQuiz();
        System.out.println(quiz);
    }
}