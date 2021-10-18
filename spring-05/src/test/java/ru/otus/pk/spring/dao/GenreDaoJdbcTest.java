package ru.otus.pk.spring.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.pk.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    public static final Long EXISTING_GENRE_ID = 1L;
    public static final String EXISTING_GENRE_NAME = "Fantasy";
    public static final Long DELETABLE_GENRE_ID = 2L;
    public static final String DELETABLE_GENRE_NAME = "Deletable";

    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        int expectedCount = 2;

        int actualCount = dao.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre genre = new Genre(null, "Sci-fi");
        Long id = dao.insert(genre);

        Genre actualGenre = dao.getById(id);
        assertThat(actualGenre.getName()).isEqualTo(genre.getName());
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        Genre expectedGenre1 = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre expectedGenre2 = new Genre(DELETABLE_GENRE_ID, DELETABLE_GENRE_NAME);
        List<Genre> actualGenreList = dao.getAll();
        Assertions.assertThat(actualGenreList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre1, expectedGenre2);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = dao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("редактировать жанр в БД")
    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, "Novel");
        dao.update(expectedGenre);
        Genre actualGenre = dao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectyDeleteGenreById() {
        assertThatCode(() -> dao.getById(DELETABLE_GENRE_ID)).doesNotThrowAnyException();

        dao.deleteById(DELETABLE_GENRE_ID);

        assertThatThrownBy(() -> dao.getById(DELETABLE_GENRE_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}