package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Volunteer;
import ru.otus.pk.spring.repository.VolunteerRepository;

import java.util.List;

import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с волонтёрами должен ")
@SpringBootTest(classes = VolunteerServiceImpl.class)
class VolunteerServiceImplTest {

    private static final Volunteer EXPECTED_VOLUNTEER = new Volunteer(1L, "Volunteer1", "89107776655");

    @MockBean
    private VolunteerRepository repository;

    @Autowired
    private VolunteerServiceImpl service;

    @DisplayName("возвращать ожидаемый список волонтёров")
    @Test
    void shouldReturnExpectedVolunteersList() {
        given(repository.findAll()).willReturn(List.of(EXPECTED_VOLUNTEER));

        List<Volunteer> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_VOLUNTEER);
    }
}