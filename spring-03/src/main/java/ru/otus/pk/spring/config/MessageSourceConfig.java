package ru.otus.pk.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.pk.spring.domain.UserLocale;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

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