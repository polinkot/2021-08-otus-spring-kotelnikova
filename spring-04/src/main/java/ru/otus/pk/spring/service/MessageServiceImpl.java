package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.config.UserLocale;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageSource messageSource;
    private final UserLocale userLocale;

    public String getMessage(String key) {
        return messageSource.getMessage(key, new Object[]{}, userLocale.getLocale());
    }

    public String getMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, userLocale.getLocale());
    }
}