package ru.otus.pk.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.pk.spring.integration.IntegrationService;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(App.class, args);

        IntegrationService integrationService = context.getBean(IntegrationService.class);
        integrationService.start();
    }
}
