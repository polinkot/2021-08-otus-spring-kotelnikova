package ru.otus.pk.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.service.InOutService;
import ru.otus.pk.spring.service.QuizService;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        QuizService service = context.getBean(QuizService.class);
        try {
            service.interview();
        } catch (AppException ae) {
            context.getBean(InOutService.class).getOut().println("\n" + ae.getMessage());
        }
    }
}