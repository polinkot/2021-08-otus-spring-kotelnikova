package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с жанрами должен ")
@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    private static final Genre GENRE1 = new Genre(ObjectId.get().toString(), "Genre1");
    private static final Genre GENRE2 = new Genre(ObjectId.get().toString(), "Genre2");

    private static final Author AUTHOR = new Author(ObjectId.get().toString(), "AuthorF1", "AuthorL1");
    private static final Book BOOK = new Book(ObjectId.get().toString(), "Book1", AUTHOR, GENRE1);

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentService commentService;

    @Autowired
    private GenreServiceImpl service;

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {
        int expectedCount = 2;

        given(bookRepository.findAllGenres()).willReturn(List.of(GENRE1, GENRE2));

        Integer actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        given(bookRepository.findAllGenres()).willReturn(List.of(GENRE1, GENRE2));

        List<Genre> actualGenres = service.findAll();
        Assertions.assertThat(actualGenres).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(GENRE1, GENRE2);
    }

    @DisplayName("возвращать ожидаемый жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        given(bookRepository.findFirstByGenreId(GENRE1.getId())).willReturn(Optional.of(BOOK));

        Genre actualGenre = service.findById(GENRE1.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(GENRE1);
    }
}