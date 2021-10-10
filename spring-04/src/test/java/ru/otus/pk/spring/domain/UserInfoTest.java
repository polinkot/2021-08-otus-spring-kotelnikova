package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс UserInfo")
class UserInfoTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        UserInfo userInfo = new UserInfo("ivan", "petrov");

        assertThat(userInfo)
                .hasFieldOrPropertyWithValue("firstName", "ivan")
                .hasFieldOrPropertyWithValue("lastName", "petrov");
    }
}