package ru.otus.pk.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "quiz")
@Component
public class QuizConfig {

    private Correct correct;

    @Getter
    @Setter
    public static class Correct {
        private int min;
    }
}
