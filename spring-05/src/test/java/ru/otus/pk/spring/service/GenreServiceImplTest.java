package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.dao.GenreDao;
import ru.otus.pk.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс GenreServiceImpl")
@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    @MockBean
    private GenreDao dao;

    @Autowired
    private GenreServiceImpl service;

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        Genre genre = new Genre(1L, "new genre");

        given(dao.getAll()).willReturn(List.of(genre));

        List<Genre> actualList = service.getAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(genre);
    }

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {
        int expectedCount = 3;

        given(dao.count()).willReturn(expectedCount);

        int actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(1L, "genre");

        given(dao.getById(expectedGenre.getId())).willReturn(expectedGenre);

        Genre actualGenre = service.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("добавлять жанр")
    @Test
    void shouldInsertGenre() {
        Long id = 1L;
        String name = "genre";

        given(dao.insert(any(Genre.class))).willReturn(id);

        Long actualId = service.insert(name);
        assertThat(actualId).isEqualTo(id);
    }

    @DisplayName("редактировать жанр")
    @Test
    void shouldUpdateGenre() {
        Long id = 1L;
        String name = "genre";

        int expectedUpdatedCount = 1;
        given(dao.update(any(Genre.class))).willReturn(expectedUpdatedCount);

        int actualUpdatedCount = service.update(id, name);
        assertThat(actualUpdatedCount).isEqualTo(expectedUpdatedCount);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectlyDeleteGenreById() {
        Long id = 1L;
        int expectedDeletedCount = 1;

        given(dao.deleteById(id)).willReturn(expectedDeletedCount);

        int actualDeletedCount = service.deleteById(id);
        assertThat(actualDeletedCount).isEqualTo(expectedDeletedCount);
    }
}