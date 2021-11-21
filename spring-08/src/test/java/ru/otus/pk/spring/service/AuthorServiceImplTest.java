package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.dto.AuthorDto;
import ru.otus.pk.spring.repository.AuthorRepository;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;
import ru.otus.pk.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с авторами должен ")
@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    private static final Author EXPECTED_AUTHOR = new Author("AuthorF", "AuthorL");
    private static final AuthorDto EXPECTED_AUTHOR_DTO = new AuthorDto("507f191e810c19729de860ea", "AuthorF", "AuthorL");

    @MockBean
    private AuthorRepository repository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentRepository commentRepository;

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
        given(repository.getAll()).willReturn(List.of(EXPECTED_AUTHOR_DTO));

        List<AuthorDto> actualList = service.getAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_AUTHOR_DTO);
    }

    @DisplayName("возвращать ожидаемого автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        given(repository.getById(EXPECTED_AUTHOR_DTO.getId())).willReturn(Optional.of(EXPECTED_AUTHOR_DTO));

        AuthorDto actualAuthor = service.getById(EXPECTED_AUTHOR_DTO.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(EXPECTED_AUTHOR_DTO);
    }

    @DisplayName("добавлять автора")
    @Test
    void shouldInsertAuthor() {
        given(repository.save(any(Author.class))).willReturn(EXPECTED_AUTHOR);

        Author actualAuthor = service.save(null, EXPECTED_AUTHOR.getFirstName(), EXPECTED_AUTHOR.getLastName());
        assertThat(actualAuthor).isEqualTo(EXPECTED_AUTHOR);
    }

    @DisplayName("редактировать автора")
    @Test
    void shouldUpdateAuthor() {
        Author expectedAuthor = new Author("changedF", "AuthorL");
        expectedAuthor.setId("507f191e810c19729de860ea");

        given(repository.findById(expectedAuthor.getId())).willReturn(Optional.of(expectedAuthor));
        given(repository.save(any(Author.class))).willReturn(expectedAuthor);

        Author actualAuthor = service.save(expectedAuthor);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
}