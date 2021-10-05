package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.UserInfo;

import static ru.otus.pk.spring.config.MessageSourceConfig.QUIZ_FIRSTNAME;
import static ru.otus.pk.spring.config.MessageSourceConfig.QUIZ_LASTNAME;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final MessageFacade messageFacade;

    public UserInfo requestUserInfo() {
        return new UserInfo(requestFirstName(), requestLastName());
    }

    private String requestFirstName() {
        messageFacade.printlnLocalized(QUIZ_FIRSTNAME);
        return messageFacade.readLine();
    }

    private String requestLastName() {
        messageFacade.printlnLocalized(QUIZ_LASTNAME);
        return messageFacade.readLine();
    }
}