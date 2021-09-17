package ru.otus.pk.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Класс Result")
class ResultTest {

    @DisplayName("корректно устанавливает имя")
    @Test
    void shouldSetCorrectFirstName() {
        String ivan = "Ivan";

        Result result = new Result();
        result.setFirstName(ivan);

        assertThat(result.getFirstName()).isEqualTo(ivan);
    }

    @DisplayName("корректно устанавливает фамилию")
    @Test
    void shouldSetCorrectLastName() {
        String petrov = "Petrov";

        Result result = new Result();
        result.setLastName(petrov);

        assertThat(result.getLastName()).isEqualTo(petrov);
    }

    @DisplayName("корректно увеличивает число правильных ответов")
    @Test
    void shouldIncreaseCorrectCount() {
        Result result = new Result();
        result.increaseCorrectAnswers();

        assertThat(result.getCorrectAnswers()).isEqualTo(1);
    }
}