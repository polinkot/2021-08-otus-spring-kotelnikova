package ru.otus.pk.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "quiz")
@Component
public class QuizConfig {

    private Map<String, String> csvSources;
    private int passGrade;
}