package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.UserInfo;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final InOutService ioService;
    private final MessageSourceAccessor messageSourceAccessor;

    public UserInfo requestUserInfo() {
        return new UserInfo(requestFirstName(), requestLastName());
    }

    private String requestFirstName() {
        ioService.println(getMessage("quiz.firstname"));
        return ioService.nextLine();
    }

    private String requestLastName() {
        ioService.println(getMessage("quiz.lastname"));
        return ioService.nextLine();
    }

    private String getMessage(String key) {
        return messageSourceAccessor.getMessage(key);
    }
}