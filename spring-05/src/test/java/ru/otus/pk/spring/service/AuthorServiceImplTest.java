package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.dao.AuthorDao;
import ru.otus.pk.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс AuthorServiceImpl")
@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @MockBean
    private AuthorDao dao;

    @Autowired
    private AuthorServiceImpl service;

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        Author author = new Author(1L, "Petr", "Petrov");

        given(dao.getAll()).willReturn(List.of(author));

        List<Author> actualList = service.getAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(author);
    }

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int expectedCount = 3;

        given(dao.count()).willReturn(expectedCount);

        int actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(1L, "Petr", "Petrov");

        given(dao.getById(expectedAuthor.getId())).willReturn(expectedAuthor);

        Author actualAuthor = service.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("добавлять автора")
    @Test
    void shouldInsertAuthor() {
        Long id = 1L;
        String firstName = "Petr";
        String lastName = "Petrov";

        given(dao.insert(any(Author.class))).willReturn(id);

        Long actualId = service.insert(firstName, lastName);
        assertThat(actualId).isEqualTo(id);
    }

    @DisplayName("редактировать автора")
    @Test
    void shouldUpdateAuthor() {
        Long id = 1L;
        String firstName = "Petr";
        String lastName = "Petrov";

        int expectedUpdatedCount = 1;
        given(dao.update(any(Author.class))).willReturn(expectedUpdatedCount);

        int actualUpdatedCount = service.update(id, firstName, lastName);
        assertThat(actualUpdatedCount).isEqualTo(expectedUpdatedCount);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectlyDeleteAuthorById() {
        Long id = 1L;
        int expectedDeletedCount = 1;

        given(dao.deleteById(id)).willReturn(expectedDeletedCount);

        int actualDeletedCount = service.deleteById(id);
        assertThat(actualDeletedCount).isEqualTo(expectedDeletedCount);
    }
}