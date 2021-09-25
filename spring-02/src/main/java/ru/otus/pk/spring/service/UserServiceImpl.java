package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.UserInfo;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final InOutService ioService;

    public UserInfo requestUserInfo() {
        return new UserInfo(requestFirstName(), requestLastName());
    }

    private String requestFirstName() {
        ioService.println("Please, input your first name: ");
        return ioService.nextLine();
    }

    private String requestLastName() {
        ioService.println("Please, input your last name: ");
        return ioService.nextLine();
    }
}