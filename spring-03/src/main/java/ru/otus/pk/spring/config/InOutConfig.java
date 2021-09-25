package ru.otus.pk.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.pk.spring.service.InOutService;
import ru.otus.pk.spring.service.InOutServiceStreams;

@RequiredArgsConstructor
@Configuration
public class InOutConfig {
    private final MessageSourceAccessor messageSourceAccessor;

    @Bean
    public InOutService getInOutService() {
        return new InOutServiceStreams(System.in, System.out, messageSourceAccessor);
    }
}