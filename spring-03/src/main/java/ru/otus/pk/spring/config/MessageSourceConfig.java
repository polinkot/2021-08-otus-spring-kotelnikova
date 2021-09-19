package ru.otus.pk.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    @Bean("messageSourceAccessor")
    @ConditionalOnProperty(value = "app.locale", havingValue = "ru")
    public MessageSourceAccessor getMessageSourceAccessorRu(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, Locale.forLanguageTag("ru-RU"));
    }

    @Bean("messageSourceAccessor")
    @ConditionalOnProperty(value = "app.locale", havingValue = "en", matchIfMissing = true)
    public MessageSourceAccessor getMessageSourceAccessorEn(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, Locale.US);
    }
}