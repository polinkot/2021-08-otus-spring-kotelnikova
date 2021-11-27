package ru.otus.pk.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataMongoTest
class BookRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private BookRepository repository;

    @DisplayName("получать всех авторов")
    @Test
    void shouldFindAllAuthors() {
        List<Author> authors = repository.findAllAuthors();
        assertThat(authors).isNotEmpty().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !isEmpty(a.getFirstName()))
                .allMatch(a -> !isEmpty(a.getLastName()))
                .anyMatch(a -> a.getFirstName().equals("AuthorF1"))
                .anyMatch(a -> a.getFirstName().equals("AuthorF2"))
                .anyMatch(a -> a.getLastName().equals("AuthorL1"))
                .anyMatch(a -> a.getLastName().equals("AuthorL2"));
    }

    @DisplayName("получать все жанры")
    @Test
    void shouldFindAllGenres() {
        List<Genre> genres = repository.findAllGenres();
        assertThat(genres).isNotEmpty().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(g -> !isEmpty(g.getName()))
                .anyMatch(g -> g.getName().equals("Genre1"))
                .anyMatch(g -> g.getName().equals("Genre2"));
    }
}