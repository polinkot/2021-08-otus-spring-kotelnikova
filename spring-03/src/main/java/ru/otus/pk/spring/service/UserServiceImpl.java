package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.UserInfo;

import static ru.otus.pk.spring.config.MessageSourceConfig.QUIZ_FIRSTNAME;
import static ru.otus.pk.spring.config.MessageSourceConfig.QUIZ_LASTNAME;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final LocalizedIOService localizedIOService;

    public UserInfo requestUserInfo() {
        return new UserInfo(requestFirstName(), requestLastName());
    }

    private String requestFirstName() {
        localizedIOService.printlnLocalized(QUIZ_FIRSTNAME);
        return localizedIOService.readLine();
    }

    private String requestLastName() {
        localizedIOService.printlnLocalized(QUIZ_LASTNAME);
        return localizedIOService.readLine();
    }
}