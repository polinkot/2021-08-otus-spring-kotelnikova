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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static ru.otus.pk.spring.domain.Gender.MALE;

@DisplayName("Сервис для работы с собаками должен ")
@SpringBootTest(classes = DogServiceImpl.class)
class DogServiceImplTest {

    private static final Dog DOG = new Dog(1L, "Dog1", MALE, 2, true, true);

    @MockBean
    private DogRepository repository;

    @Autowired
    private DogServiceImpl service;

    @DisplayName("возвращать ожидаемый список собак")
    @Test
    void shouldReturnExpectedDogsList() {
        given(repository.findAll()).willReturn(List.of(DOG));

        List<Dog> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(DOG);
    }

    @DisplayName("возвращать ожидаемую собаку по id")
    @Test
    void shouldReturnExpectedDogById() {
        given(repository.findById(DOG.getId())).willReturn(Optional.of(DOG));

        Dog actualDog = service.findById(DOG.getId());
        assertThat(actualDog).usingRecursiveComparison().isEqualTo(DOG);
    }

    @DisplayName("добавлять собаку")
    @Test
    void shouldAddDog() {
        given(repository.save(any(Dog.class))).willReturn(DOG);

        Dog actualDog = service.save(new Dog(null, "Dog1", MALE, 1, true, true));
        assertThat(actualDog).isEqualTo(DOG);
    }

    @DisplayName("редактировать собаку")
    @Test
    void shouldUpdateDog() {
        given(repository.findById(DOG.getId())).willReturn(Optional.of(DOG));
        given(repository.save(any(Dog.class))).willReturn(DOG);

        Dog actualDog = service.save(DOG);
        assertThat(actualDog).isEqualTo(DOG);
    }
}