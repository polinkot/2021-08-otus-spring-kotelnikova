package ru.otus.pk.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    @Bean("userLocale")
    @ConditionalOnProperty(value = "lang", havingValue = "ru")
    public Locale getUserLocaleRu() {
        return Locale.forLanguageTag("ru-RU");
    }

    @Bean("userLocale")
    @ConditionalOnProperty(value = "lang", havingValue = "en", matchIfMissing = true)
    public Locale getUserLocaleEn() {
        return Locale.US;
    }

    @Bean("messageSourceAccessor")
    public MessageSourceAccessor getMessageSourceAccessor(MessageSource messageSource, Locale userLocale) {
        return new MessageSourceAccessor(messageSource, userLocale);
    }
}