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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с хозяевами должен ")
@SpringBootTest(classes = OwnerServiceImpl.class)
class OwnerServiceImplTest {

    private static final Owner OWNER = new Owner(1L, "name1", 35, "address1", "89101112233");

    @MockBean
    private OwnerRepository repository;

    @Autowired
    private OwnerServiceImpl service;

    @DisplayName("возвращать ожидаемый список хозяев")
    @Test
    void shouldReturnExpectedOwnersList() {
        given(repository.findAll()).willReturn(List.of(OWNER));

        List<Owner> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(OWNER);
    }

    @DisplayName("возвращать хозяина по id")
    @Test
    void shouldReturnExpectedOwnerById() {
        given(repository.findById(OWNER.getId())).willReturn(Optional.of(OWNER));

        Owner actualOwner = service.findById(OWNER.getId());
        assertThat(actualOwner).usingRecursiveComparison().isEqualTo(OWNER);
    }

    @DisplayName("добавлять хозяина")
    @Test
    void shouldAddOwner() {
        given(repository.save(any(Owner.class))).willReturn(OWNER);

        Owner actualOwner = service.save(new Owner(null, "owner2", 35, "address1", "89101112233"));
        assertThat(actualOwner).isEqualTo(OWNER);
    }

    @DisplayName("редактировать хозяина")
    @Test
    void shouldUpdateOwner() {
        given(repository.findById(OWNER.getId())).willReturn(Optional.of(OWNER));
        given(repository.save(any(Owner.class))).willReturn(OWNER);

        Owner actualOwner = service.save(OWNER);
        assertThat(actualOwner).isEqualTo(OWNER);
    }
}