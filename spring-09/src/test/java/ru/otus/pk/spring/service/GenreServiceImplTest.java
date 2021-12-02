package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с жанрами должен ")
@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    private static final Genre EXPECTED_GENRE = new Genre(1L, "Genre1");

    @MockBean
    private GenreRepository repository;

    @Autowired
    private GenreServiceImpl service;

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {
        Long expectedCount = 2L;

        given(repository.count()).willReturn(expectedCount);

        Long actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        given(repository.findAll()).willReturn(List.of(EXPECTED_GENRE));

        List<Genre> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_GENRE);
    }

    @DisplayName("возвращать ожидаемый жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        given(repository.findById(EXPECTED_GENRE.getId())).willReturn(Optional.of(EXPECTED_GENRE));

        Genre actualGenre = service.findById(EXPECTED_GENRE.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXPECTED_GENRE);
    }

    @DisplayName("добавлять жанр")
    @Test
    void shouldInsertGenre() {
        given(repository.save(any(Genre.class))).willReturn(EXPECTED_GENRE);

//        Genre actualGenre = service.save(null, "newGenre");
//        assertThat(actualGenre).isEqualTo(EXPECTED_GENRE);
    }

    @DisplayName("редактировать жанр")
    @Test
    void shouldUpdateGenre() {
        String changedName = "changedName";
        Genre expectedGenre = new Genre(1L, changedName);

        given(repository.findById(expectedGenre.getId())).willReturn(Optional.of(expectedGenre));
        given(repository.save(any(Genre.class))).willReturn(expectedGenre);

//        Genre actualGenre = service.save(1L, changedName);
//        assertThat(actualGenre).isEqualTo(expectedGenre);
    }
}