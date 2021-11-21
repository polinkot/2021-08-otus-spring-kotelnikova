package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.GenreDto;
import ru.otus.pk.spring.repository.AuthorRepository;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;
import ru.otus.pk.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с жанрами должен ")
@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    private static final Genre EXPECTED_GENRE = new Genre("Genre1");
    private static final GenreDto EXPECTED_GENRE_DTO = new GenreDto("507f191e810c19729de860ea", "Genre1");

    @MockBean
    private GenreRepository repository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private GenreServiceImpl service;

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {
        Long expectedCount = 2L;

        given(repository.count()).willReturn(expectedCount);

        Long actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        given(repository.getAll()).willReturn(List.of(EXPECTED_GENRE_DTO));

        List<GenreDto> actualList = service.getAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_GENRE_DTO);
    }

    @DisplayName("возвращать ожидаемый жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        given(repository.getById(EXPECTED_GENRE_DTO.getId())).willReturn(Optional.of(EXPECTED_GENRE_DTO));

        GenreDto actualGenre = service.getById(EXPECTED_GENRE_DTO.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXPECTED_GENRE_DTO);
    }

    @DisplayName("добавлять жанр")
    @Test
    void shouldInsertGenre() {
        given(repository.save(any(Genre.class))).willReturn(EXPECTED_GENRE);

        Genre actualGenre = service.save(null, "newGenre");
        assertThat(actualGenre).isEqualTo(EXPECTED_GENRE);
    }

    @DisplayName("редактировать жанр")
    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre("changedName");
        expectedGenre.setId("507f191e810c19729de860ea");

        given(repository.findById(expectedGenre.getId())).willReturn(Optional.of(expectedGenre));
        given(repository.save(any(Genre.class))).willReturn(expectedGenre);

        Genre actualGenre = service.save(expectedGenre);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }
}