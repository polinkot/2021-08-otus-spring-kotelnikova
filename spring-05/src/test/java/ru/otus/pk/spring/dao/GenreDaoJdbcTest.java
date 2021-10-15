package ru.otus.pk.spring.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.domain.Genre;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    public static final Long EXISTING_GENRE_ID = 1L;
    public static final String EXISTING_GENRE_NAME = "Fantasy";

    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        int expectedCount = 1;

        int actualCount = dao.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(2L, "Sci-fi");
        dao.insert(expectedGenre);
        Genre actualPerson = dao.getById(expectedGenre.getId());
        Assertions.assertThat(actualPerson).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getAll() {
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = dao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}