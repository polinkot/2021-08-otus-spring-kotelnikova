package ru.otus.pk.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    public static final String QUIZ_FIRSTNAME = "quiz.firstname";
    public static final String QUIZ_LASTNAME = "quiz.lastname";
    public static final String QUIZ_QUESTIONS = "quiz.questions";
    public static final String QUIZ_RESULT = "quiz.result";
    public static final String QUIZ_SUCCESS = "quiz.success";
    public static final String QUIZ_FAILURE = "quiz.failure";
    public static final String QUIZ_ENTER_INTEGER = "quiz.enter-integer";
    public static final String QUIZ_INCORRECT_FORMAT = "quiz.incorrect-format";

    public static final String DEFAULT_LANG = "en-US";

    @Bean
    public UserLocale getUserLocale(@Value("${user.lang:" + DEFAULT_LANG + "}") String languageTag) {
        return new UserLocale(Locale.forLanguageTag(languageTag));
    }
}