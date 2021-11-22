package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.pk.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с жанрами должен ")
@DataMongoTest
class GenreRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private GenreRepository repository;

    @DisplayName("загружать дто жанра по id")
    @Test
    void shouldGetExpectedGenreById() {
        val all = repository.findAll();
        Genre genre = all.get(0);

        val actualGenre = repository.getById(genre.getId());
        assertThat(actualGenre).isPresent().get()
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @DisplayName("загружать список дто жанров")
    @Test
    void getAll() {
        val genres = repository.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(g -> !isEmpty(g.getName()));
    }
}