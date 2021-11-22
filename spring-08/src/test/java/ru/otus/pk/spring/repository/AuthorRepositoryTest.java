package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.pk.spring.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с авторами должен ")
@DataMongoTest
class AuthorRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 4;

    @Autowired
    private AuthorRepository repository;

    @DisplayName("загружать дто автора по id")
    @Test
    void shouldGetExpectedAuthorById() {
        val all = repository.findAll();
        Author author = all.get(0);

        val actualAuthor = repository.getById(author.getId());
        assertThat(actualAuthor).isPresent().get()
                .hasFieldOrPropertyWithValue("firstName", author.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", author.getLastName());
    }

    @DisplayName("загружать список дто авторов")
    @Test
    void getAll() {
        val authors = repository.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !isEmpty(a.getFirstName()))
                .allMatch(a -> !isEmpty(a.getLastName()));
    }
}