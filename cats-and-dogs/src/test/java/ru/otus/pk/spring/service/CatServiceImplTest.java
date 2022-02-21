package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Cat;
import ru.otus.pk.spring.repository.CatRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static ru.otus.pk.spring.domain.Gender.FEMALE;

@DisplayName("Сервис для работы с кошками должен ")
@SpringBootTest(classes = CatServiceImpl.class)
class CatServiceImplTest {

    private static final Cat CAT = new Cat(1L, "Cat1", FEMALE, 1, true, true);

    @MockBean
    private CatRepository repository;

    @Autowired
    private CatServiceImpl service;

    @DisplayName("возвращать ожидаемый список кошек")
    @Test
    void shouldReturnExpectedCatsList() {
        given(repository.findAll()).willReturn(List.of(CAT));

        List<Cat> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(CAT);
    }
}