package ru.otus.pk.spring.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.model.Genre;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final long EXISTING_GENRE_ID = 1L;
    public static final Long DELETABLE_GENRE_ID = 3L;

    private static final int EXPECTED_NUMBER_OF_GENRES = 3;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private GenreRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном жанре по id")
    @Test
    void shouldFindExpectedGenreById() {
        val actualGenre = repository.findById(EXISTING_GENRE_ID);
        val expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(actualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val genres = repository.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES).allMatch(a -> !a.getName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        String name = "Ivan";

        Genre newGenre = new Genre(null, name, emptyList());
        Genre savedGenre = repository.save(newGenre);

        Genre fetchedGenre = em.find(Genre.class, savedGenre.getId());
        assertThat(fetchedGenre).isNotNull();
        assertThat(fetchedGenre.getName()).isEqualTo(name);
    }

    @DisplayName("редактировать жанр в БД")
    @Test
    void shouldUpdateGenre() {
        Genre changedGenre = new Genre(EXISTING_GENRE_ID, "changedGenre", emptyList());
        Genre savedGenre = repository.save(changedGenre);

        Genre fetchedGenre = em.find(Genre.class, savedGenre.getId());
        assertThat(fetchedGenre).usingRecursiveComparison().isEqualTo(changedGenre);
    }

    @DisplayName("удалять заданный жанр по id")
    @Test
    void shouldCorrectyDeleteGenreById() {
        Genre fetchedGenre = em.find(Genre.class, DELETABLE_GENRE_ID);
        assertThat(fetchedGenre).isNotNull();

        repository.deleteById(DELETABLE_GENRE_ID);
        em.detach(fetchedGenre);

        fetchedGenre = em.find(Genre.class, DELETABLE_GENRE_ID);
        assertThat(fetchedGenre).isNull();
    }
}