package ru.otus.pk.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.pk.spring.service.InOutService;
import ru.otus.pk.spring.service.InOutServiceImpl;

@Configuration
public class InOutConfig {

    @Bean
    public InOutService getInOutService() {
        return new InOutServiceImpl(System.out);
    }
}
