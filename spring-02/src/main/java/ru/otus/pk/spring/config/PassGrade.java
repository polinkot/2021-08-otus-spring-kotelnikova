package ru.otus.pk.spring.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//У нас еще нет @ConfigurationProperties в этом ДЗ. Сделала просто компонент.
@Data
@Component
public class PassGrade {

    private final int value;

    public PassGrade(@Value("${questions.pass.grade}") int value) {
        this.value = value;
    }
}