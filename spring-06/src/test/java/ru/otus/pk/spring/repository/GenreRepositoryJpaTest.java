package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с жанрами должен ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final Long EXISTING_GENRE_ID = 1L;
    public static final Long DELETABLE_GENRE_ID = 3L;

    private static final int EXPECTED_NUMBER_OF_GENRES = 3;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private GenreRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long actualCount = repository.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_GENRES);
    }

    @DisplayName("загружать жанр по id")
    @Test
    void shouldFindExpectedGenreById() {
        val actualGenre = repository.findById(EXISTING_GENRE_ID);
        val expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(actualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        Statistics statistics = new Statistics(em);

        val genres = repository.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES).allMatch(g -> !isEmpty(g.getName()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        String name = "newName";
        Genre savedGenre = repository.save(new Genre(null, name));

        Genre actualGenre = em.find(Genre.class, savedGenre.getId());
        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getName()).isEqualTo(name);
    }

    @DisplayName("редактировать жанр в БД")
    @Test
    void shouldUpdateGenre() {
        Genre existingGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        existingGenre.setName("changedName");
        Genre savedGenre = repository.save(existingGenre);

        Genre actualGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(savedGenre);
    }

    @DisplayName("удалять жанр по id")
    @Test
    void shouldCorrectyDeleteGenreById() {
        Genre genre = em.find(Genre.class, DELETABLE_GENRE_ID);
        assertThat(genre).isNotNull();

        repository.deleteById(DELETABLE_GENRE_ID);
        em.detach(genre);

        genre = em.find(Genre.class, DELETABLE_GENRE_ID);
        assertThat(genre).isNull();
    }

    @DisplayName("возвращать ожидаемый список книг для жанра ")
    @Test
    void shouldReturnExpectedGenreBooksCount() {
        Statistics statistics = new Statistics(em);

        List<Book> books = repository.findBooks(EXISTING_GENRE_ID);

        int expectedNumberOfBooks = 1;
        int expectedQueriesCount = 1;
        assertThat(books).isNotNull().hasSize(expectedNumberOfBooks)
                .allMatch(b -> !isEmpty(b.getName()))
                .anyMatch(b -> b.getName().equals("Book2"))
                .allMatch(b -> !isEmpty(b.getGenre()))
                .allMatch(b -> !isEmpty(b.getAuthor()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(expectedQueriesCount);
    }
}