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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с авторами должен ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final Long EXISTING_AUTHOR_ID = 1L;
    public static final Long DELETABLE_AUTHOR_ID = 2L;

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private AuthorRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать ожидаемое количество авторов в БД ")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long actualCount = repository.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("загружать автора по id")
    @Test
    void shouldFindExpectedAuthorById() {
        val actualAuthor = repository.findById(EXISTING_AUTHOR_ID);
        val expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        Statistics statistics = new Statistics(em);

        val authors = repository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !isEmpty(a.getFirstName()))
                .allMatch(a -> !isEmpty(a.getLastName()));
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

    @DisplayName("редактировать автора в БД")
    @Test
    void shouldUpdateAuthorName() {
        Author existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        existingAuthor.setFirstName("updatedF");
        existingAuthor.setLastName("updatedL");
        Author savedAuthor = repository.save(existingAuthor);

        Author actualAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(savedAuthor);
    }

    @DisplayName("удалять автора по id")
    @Test
    void shouldCorrectyDeleteAuthorById() {
        Author author = em.find(Author.class, DELETABLE_AUTHOR_ID);
        assertThat(author).isNotNull();

        repository.deleteById(DELETABLE_AUTHOR_ID);
        em.detach(author);

        author = em.find(Author.class, DELETABLE_AUTHOR_ID);
        assertThat(author).isNull();
    }

    @DisplayName("возвращать ожидаемый список книг для автора ")
    @Test
    void shouldReturnExpectedAuthorBooksCount() {
        Statistics statistics = new Statistics(em);

        List<Book> books = repository.findBooks(EXISTING_AUTHOR_ID);

        int expectedNumberOfBooks = 2;
        int expectedQueriesCount = 1;
        assertThat(books).isNotNull().hasSize(expectedNumberOfBooks)
                .allMatch(b -> !isEmpty(b.getName()))
                .anyMatch(b -> b.getName().equals("Book1"))
                .anyMatch(b -> b.getName().equals("Book2"))
                .allMatch(b -> !isEmpty(b.getGenre()))
                .allMatch(b -> !isEmpty(b.getAuthor()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(expectedQueriesCount);
    }
}