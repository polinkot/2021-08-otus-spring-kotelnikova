package ru.otus.pk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.pk.spring.domain.UserInfo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс UserServiceImpl")
@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

    @MockBean
    private InOutService ioService;

    @MockBean
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private UserServiceImpl service;

    @DisplayName("корректно устанавливает данные пользователя")
    @Test
    void shouldRequestUserInfoCorrectly() {
        String ivan = "Ivan";
        String petrov = "Petrov";

        given(ioService.readLine()).willReturn(ivan, petrov);

        UserInfo userInfo = service.requestUserInfo();

        assertAll("userInfo",
                () -> assertEquals(ivan, userInfo.getFirstName()),
                () -> assertEquals(petrov, userInfo.getLastName())
        );
    }
}