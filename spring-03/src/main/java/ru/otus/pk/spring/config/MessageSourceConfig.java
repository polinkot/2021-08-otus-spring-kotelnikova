package ru.otus.pk.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

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

    @Bean("userLocale")
    @ConditionalOnProperty(value = "lang", havingValue = "ru")
    public UserLocale getUserLocaleRu() {
        return new UserLocale(Locale.forLanguageTag("ru-RU"));
    }

    @Bean("userLocale")
    @ConditionalOnProperty(value = "lang", havingValue = "en", matchIfMissing = true)
    public UserLocale getUserLocaleEn() {
        return new UserLocale(Locale.US);
    }

    @Bean("messageSourceAccessor")
    public MessageSourceAccessor getMessageSourceAccessor(MessageSource messageSource, UserLocale userLocale) {
        return new MessageSourceAccessor(messageSource, userLocale.getLocale());
    }
}