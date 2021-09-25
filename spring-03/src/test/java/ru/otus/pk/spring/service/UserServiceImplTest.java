package ru.otus.pk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.pk.spring.domain.UserInfo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс UserServiceImpl")
class UserServiceImplTest {

    private final InOutService ioService = mock(InOutService.class);
    private final MessageSourceAccessor messageSourceAccessor = mock(MessageSourceAccessor.class);

    private final UserServiceImpl service = new UserServiceImpl(ioService, messageSourceAccessor);

    @DisplayName("корректно устанавливает данные пользователя")
    @Test
    void shouldRequestUserInfoCorrectly() {
        String ivan = "Ivan";
        String petrov = "Petrov";

        given(ioService.nextLine()).willReturn(ivan, petrov);

        UserInfo userInfo = service.requestUserInfo();

        assertAll("userInfo",
                () -> assertEquals(ivan, userInfo.getFirstName()),
                () -> assertEquals(petrov, userInfo.getLastName())
        );
    }
}