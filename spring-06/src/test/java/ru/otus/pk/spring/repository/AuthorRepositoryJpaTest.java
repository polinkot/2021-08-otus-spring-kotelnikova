package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final Long EXISTING_AUTHOR_ID = 1L;
    public static final Long DELETABLE_AUTHOR_ID = 2L;

    private static final Long EXISTING_BOOK_ID = 1L;

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private AuthorRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        long actualCount = repository.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName(" должен загружать информацию о нужном авторе по id")
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
        Statistics statistics = new Statistics(em);
        statistics.setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val authors = repository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getFirstName().equals(""))
                .allMatch(a -> !a.getLastName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        String firstName = "newF";
        String lastName = "newL";
        Author savedAuthor = repository.save(new Author(null, firstName, lastName));

        Author actualAuthor = em.find(Author.class, savedAuthor.getId());
        assertThat(actualAuthor).isNotNull()
                .extracting("firstName", "lastName")
                .doesNotContainNull()
                .containsExactly(firstName, lastName);
    }

    @DisplayName("редактировать данные автора в БД")
    @Test
    void shouldUpdateAuthorName() {
        Author existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        existingAuthor.setFirstName("updatedF");
        existingAuthor.setLastName("updatedL");
        Author savedAuthor = repository.save(existingAuthor);

        Author actualAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(savedAuthor);
    }

    @DisplayName("добавить книгу автора")
    @Test
    void shouldAddBookToAuthor() {
        Author existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        existingAuthor.addBooks(Set.of(existingBook));
        repository.save(existingAuthor);

        Author actualAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(actualAuthor.getBooks()).contains(existingBook);
        assertThat(actualAuthor).isEqualTo(existingBook.getAuthor());
    }

    @DisplayName("удалить книгу автора")
    @Test
    void shouldRemoveBookFromAuthor() {
        Author existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        existingAuthor.removeBooks(Set.of(existingBook.getId()));
        repository.save(existingAuthor);

        Author actualAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(actualAuthor.getBooks()).doesNotContain(existingBook);
        assertThat(actualAuthor).isNotEqualTo(existingBook.getAuthor());
    }

    @DisplayName("удалять заданного автора по id")
    @Test
    void shouldCorrectyDeleteAuthorById() {
        Author author = em.find(Author.class, DELETABLE_AUTHOR_ID);
        assertThat(author).isNotNull();

        repository.deleteById(DELETABLE_AUTHOR_ID);
        em.detach(author);

        author = em.find(Author.class, DELETABLE_AUTHOR_ID);
        assertThat(author).isNull();
    }
}