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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final Long EXISTING_GENRE_ID = 1L;
    public static final Long DELETABLE_GENRE_ID = 3L;

    private static final Long EXISTING_BOOK_ID = 1L;

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
        Statistics statistics = new Statistics(em);
        statistics.setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val genres = repository.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES).allMatch(g -> !g.getName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
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

    @DisplayName("добавить книгу в жанр")
    @Test
    void shouldAddBookToGenre() {
        Genre existingGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        existingGenre.addBook(existingBook);
        repository.save(existingGenre);

        Genre actualGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(actualGenre.getBooks()).contains(existingBook);
        assertThat(actualGenre).isEqualTo(existingBook.getGenre());
    }

    @DisplayName("удалить книгу из жанра")
    @Test
    void shouldRemoveBookFromGenre() {
        Genre existingGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        existingGenre.removeBook(existingBook);
        repository.save(existingGenre);

        Genre actualGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(actualGenre.getBooks()).doesNotContain(existingBook);
        assertThat(actualGenre).isNotEqualTo(existingBook.getGenre());
    }

    @DisplayName("удалять заданный жанр по id")
    @Test
    void shouldCorrectyDeleteGenreById() {
        Genre genre = em.find(Genre.class, DELETABLE_GENRE_ID);
        assertThat(genre).isNotNull();

        repository.deleteById(DELETABLE_GENRE_ID);
        em.detach(genre);

        genre = em.find(Genre.class, DELETABLE_GENRE_ID);
        assertThat(genre).isNull();
    }
}