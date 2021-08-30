package ru.otus.pk.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.service.QuestionService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            printQuestions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printQuestions() throws IOException, URISyntaxException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        List<Question> questions = service.findAll();

        questions.forEach(question -> {
            System.out.printf("\n%s", question.getValue());
            question.getAnswers().forEach(answer -> System.out.printf("\n\t%s", answer.getValue()));
        });
    }
}