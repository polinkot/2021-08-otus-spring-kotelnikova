package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Dog;
import ru.otus.pk.spring.repository.DogRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с собаками должен ")
@SpringBootTest(classes = DogServiceImpl.class)
class DogServiceImplTest {

    private static final Dog EXPECTED_DOG = new Dog(1L, "Dog1");

    @MockBean
    private DogRepository repository;

    @Autowired
    private DogServiceImpl service;

    @DisplayName("возвращать ожидаемый список собак")
    @Test
    void shouldReturnExpectedDogsList() {
        given(repository.findAll()).willReturn(List.of(EXPECTED_DOG));

        List<Dog> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_DOG);
    }
}