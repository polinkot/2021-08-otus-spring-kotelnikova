package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Adoption;
import ru.otus.pk.spring.repository.AdoptionRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с пристройствами должен ")
@SpringBootTest(classes = AdoptionServiceImpl.class)
class AdoptionServiceImplTest {

    private static final Adoption ADOPTION = new Adoption(1L);

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
}