package ru.otus.pk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Animal;
import ru.otus.pk.spring.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static ru.otus.pk.spring.domain.Animal.AnimalStatus.NOT_ADOPTED;
import static ru.otus.pk.spring.domain.Gender.MALE;

@DisplayName("Сервис для работы с кошками должен ")
@SpringBootTest(classes = AnimalServiceImpl.class)
class AnimalServiceImplTest {

    private static final Animal CAT = new Animal(1L, "Cat1", MALE, 1, true, true, NOT_ADOPTED, Animal.AnimalType.CAT);

    @MockBean
    private AnimalRepository repository;

    @Autowired
    private AnimalServiceImpl service;

    @DisplayName("возвращать ожидаемый список кошек")
    @Test
    void shouldReturnExpectedCatsList() {
        given(repository.findAll()).willReturn(List.of(CAT));

        List<Animal> actualList = service.findAll();
        assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(CAT);
    }

    @DisplayName("возвращать кота по id")
    @Test
    void shouldReturnExpectedCatById() {
        given(repository.findById(CAT.getId())).willReturn(Optional.of(CAT));

        Animal actualCat = service.findById(CAT.getId());
        assertThat(actualCat).usingRecursiveComparison().isEqualTo(CAT);
    }

    @DisplayName("добавлять кота")
    @Test
    void shouldAddCat() {
        given(repository.save(any(Animal.class))).willReturn(CAT);

        Animal actualCat = service.save(new Animal(null, "Cat1", MALE, 1, true, true, NOT_ADOPTED, Animal.AnimalType.CAT));
        assertThat(actualCat).isEqualTo(CAT);
    }

    @DisplayName("редактировать кота")
    @Test
    void shouldUpdateCat() {
        given(repository.findById(CAT.getId())).willReturn(Optional.of(CAT));
        given(repository.save(any(Animal.class))).willReturn(CAT);

        Animal actualCat = service.save(CAT);
        assertThat(actualCat).isEqualTo(CAT);
    }
}