package ru.otus.pk.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.pk.spring.integration.IntegrationService;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        IntegrationService integrationService = ctx.getBean(IntegrationService.class);
        integrationService.start();
    }
}
