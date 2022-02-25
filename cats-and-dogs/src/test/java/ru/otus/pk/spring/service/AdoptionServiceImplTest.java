package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.repository.AdoptionRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static ru.otus.pk.spring.domain.Gender.FEMALE;

@DisplayName("Сервис для работы с пристройствами должен ")
@SpringBootTest(classes = AdoptionServiceImpl.class)
class AdoptionServiceImplTest {

    private static final Cat CAT = new Cat(1L, "Cat1", FEMALE, 1, true, true);
    private static final Owner OWNER = new Owner(1L, "name1", 35, "address1", "89101112233");
    private static final Adoption ADOPTION = new Adoption(1L, CAT, OWNER);

    @MockBean
    private AdoptionRepository repository;

    @Autowired
    private AdoptionServiceImpl service;

    @DisplayName("возвращать ожидаемый список пристройств")
    @Test
    void shouldReturnExpectedAdoptionsList() {
        given(repository.findAll()).willReturn(List.of(ADOPTION));

        List<Adoption> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(ADOPTION);
    }

    @DisplayName("возвращать пристройство по id")
    @Test
    void shouldReturnExpectedAdoptionById() {
        given(repository.findById(ADOPTION.getId())).willReturn(Optional.of(ADOPTION));

        Adoption actualAdoption = service.findById(ADOPTION.getId());
        assertThat(actualAdoption).usingRecursiveComparison().isEqualTo(ADOPTION);
    }

    @DisplayName("добавлять пристройство")
    @Test
    void shouldAddAdoption() {
        given(repository.save(any(Adoption.class))).willReturn(ADOPTION);

        Adoption actualAdoption = service.save(new Adoption(null, CAT, OWNER));
        assertThat(actualAdoption).isEqualTo(ADOPTION);
    }
}