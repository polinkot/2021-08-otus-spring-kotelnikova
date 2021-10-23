package ru.otus.pk.spring.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.pk.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    public static final Long EXISTING_AUTHOR_ID = 1L;
    public static final String EXISTING_AUTHOR_FIRST_NAME = "Petr";
    public static final String EXISTING_AUTHOR_LAST_NAME = "Petrov";
    public static final Long DELETABLE_AUTHOR_ID = 2L;
    public static final String DELETABLE_AUTHOR_FIRST_NAME = "Deletable";
    public static final String DELETABLE_AUTHOR_LAST_NAME = "Deletable";

    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int expectedCount = 2;

        int actualCount = dao.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        Author expectedAuthor1 = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRST_NAME, EXISTING_AUTHOR_LAST_NAME);
        Author expectedAuthor2 = new Author(DELETABLE_AUTHOR_ID, DELETABLE_AUTHOR_FIRST_NAME, DELETABLE_AUTHOR_LAST_NAME);

        List<Author> actualAuthorList = dao.getAll();
        Assertions.assertThat(actualAuthorList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedAuthor1, expectedAuthor2);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FIRST_NAME, EXISTING_AUTHOR_LAST_NAME);
        Author actualAuthor = dao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author author = new Author(null, "Ivan", "Ivanov");
        Long id = dao.insert(author);

        Author actualAuthor = dao.getById(id);
        assertThat(actualAuthor).extracting("firstName", "lastName")
                .doesNotContainNull()
                .containsExactly("Ivan", "Ivanov");
    }

    @DisplayName("редактировать автора в БД")
    @Test
    void shouldUpdateAuthor() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, "Igor", "Petrov");
        dao.update(expectedAuthor);
        Author actualAuthor = dao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectyDeleteAuthorById() {
        assertThatCode(() -> dao.getById(DELETABLE_AUTHOR_ID)).doesNotThrowAnyException();

        dao.deleteById(DELETABLE_AUTHOR_ID);

        assertThatThrownBy(() -> dao.getById(DELETABLE_AUTHOR_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}