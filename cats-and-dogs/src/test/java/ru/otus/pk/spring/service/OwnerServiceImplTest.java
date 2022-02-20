package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Owner;
import ru.otus.pk.spring.repository.OwnerRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с хозяевами должен ")
@SpringBootTest(classes = OwnerServiceImpl.class)
class OwnerServiceImplTest {

    private static final Owner EXPECTED_OWNER = new Owner(1L);

    @MockBean
    private OwnerRepository repository;

    @Autowired
    private OwnerServiceImpl service;

    @DisplayName("возвращать ожидаемый список хозяев")
    @Test
    void shouldReturnExpectedOwnersList() {
        given(repository.findAll()).willReturn(List.of(EXPECTED_OWNER));

        List<Owner> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_OWNER);
    }
}