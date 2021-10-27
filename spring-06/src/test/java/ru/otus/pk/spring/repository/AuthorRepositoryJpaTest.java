package ru.otus.pk.spring.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.model.Author;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final long EXISTING_AUTHOR_ID = 1L;
    public static final Long DELETABLE_AUTHOR_ID = 2L;

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private AuthorRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        val actualAuthor = repository.findById(EXISTING_AUTHOR_ID);
        val expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов с полной информацией о них")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val authors = repository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getFirstName().equals(""))
                .allMatch(a -> !a.getLastName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        String firstName = "Ivan";
        String lastName = "Ivanov";

        Author newAuthor = new Author(null, firstName, lastName, emptyList());
        Author savedAuthor = repository.save(newAuthor);

        Author fetchedAuthor = em.find(Author.class, savedAuthor.getId());
        assertThat(fetchedAuthor).extracting("firstName", "lastName")
                .doesNotContainNull()
                .containsExactly(firstName, lastName);
    }

    @DisplayName("редактировать автора в БД")
    @Test
    void shouldUpdateAuthor() {
        Author changedAuthor = new Author(EXISTING_AUTHOR_ID, "Igor", "Petrov", emptyList());
        Author savedAuthor = repository.save(changedAuthor);

        Author fetchedAuthor = em.find(Author.class, savedAuthor.getId());
        assertThat(fetchedAuthor).usingRecursiveComparison().isEqualTo(changedAuthor);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectyDeleteAuthorById() {
        Author fetchedAuthor = em.find(Author.class, DELETABLE_AUTHOR_ID);
        assertThat(fetchedAuthor).isNotNull();

        repository.deleteById(DELETABLE_AUTHOR_ID);
        em.detach(fetchedAuthor);

        fetchedAuthor = em.find(Author.class, DELETABLE_AUTHOR_ID);
        assertThat(fetchedAuthor).isNull();
    }
}