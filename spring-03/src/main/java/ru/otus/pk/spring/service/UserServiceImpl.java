package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.UserInfo;

import static ru.otus.pk.spring.config.MessageSourceConfig.QUIZ_FIRSTNAME;
import static ru.otus.pk.spring.config.MessageSourceConfig.QUIZ_LASTNAME;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final InOutService ioService;
    private final MessageSourceAccessor messageSourceAccessor;

    public UserInfo requestUserInfo() {
        return new UserInfo(requestFirstName(), requestLastName());
    }

    private String requestFirstName() {
        ioService.println(getMessage(QUIZ_FIRSTNAME));
        return ioService.readLine();
    }

    private String requestLastName() {
        ioService.println(getMessage(QUIZ_LASTNAME));
        return ioService.readLine();
    }

    private String getMessage(String key) {
        return messageSourceAccessor.getMessage(key);
    }
}