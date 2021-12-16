package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с авторами должен ")
@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    private static final Author EXPECTED_AUTHOR = new Author(1L, "AuthorF", "AuthorL");

    @MockBean
    private AuthorRepository repository;

    @Autowired
    private AuthorServiceImpl service;

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long expectedCount = 2L;

        given(repository.count()).willReturn(expectedCount);

        Long actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        given(repository.findAll()).willReturn(List.of(EXPECTED_AUTHOR));

        List<Author> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_AUTHOR);
    }

    @DisplayName("возвращать ожидаемого автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        given(repository.findById(EXPECTED_AUTHOR.getId())).willReturn(Optional.of(EXPECTED_AUTHOR));

        Author actualAuthor = service.findById(EXPECTED_AUTHOR.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(EXPECTED_AUTHOR);
    }

    @DisplayName("добавлять автора")
    @Test
    void shouldAddAuthor() {
        given(repository.save(any(Author.class))).willReturn(EXPECTED_AUTHOR);

        Author actualAuthor = service.save(new Author(10L, "author1F", "author1L"));
        assertThat(actualAuthor).isEqualTo(EXPECTED_AUTHOR);
    }

    @DisplayName("редактировать автора")
    @Test
    void shouldUpdateAuthor() {
        Author expectedAuthor = new Author(1L, "changedF", "AuthorL");

        given(repository.findById(expectedAuthor.getId())).willReturn(Optional.of(expectedAuthor));
        given(repository.save(any(Author.class))).willReturn(expectedAuthor);

        Author actualAuthor = service.save(expectedAuthor);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
}