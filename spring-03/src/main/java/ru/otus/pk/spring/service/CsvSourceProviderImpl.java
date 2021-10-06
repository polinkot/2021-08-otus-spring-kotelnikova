package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.pk.spring.config.QuizConfig;
import ru.otus.pk.spring.config.UserLocale;

@RequiredArgsConstructor
@Component
public class CsvSourceProviderImpl implements CsvSourceProvider {

    private final QuizConfig quizConfig;
    private final UserLocale userLocale;

    public String getCsvSource() {
        return quizConfig.getCsvSources().get(userLocale.getLocale().getLanguage());
    }
}
