package ru.otus.pk.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.service.QuestionService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context =
                    new ClassPathXmlApplicationContext("/spring-context.xml");
            QuestionService service = context.getBean(QuestionService.class);
            List<Question> questions = service.findAll();

            questions.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}