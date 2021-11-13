package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.pk.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataJpaTest
@AutoConfigureTestDatabase(connection = H2, replace = AUTO_CONFIGURED)
class BookRepositoryTest {

    private static final Long EXISTING_BOOK_ID_1 = 1L;

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_GENRE_ID = 1L;

    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружать книгу по id")
    @Test
    void shouldFindExpectedBookById() {
        val actualBook = repository.findById(EXISTING_BOOK_ID_1);
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID_1);
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("загружать список всех книг")
    @Test
    void findAll() {
        Statistics statistics = new Statistics(em);

        val books = repository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !isEmpty(b.getName()))
                .anyMatch(b -> b.getName().equals("Book1"))
                .anyMatch(b -> b.getName().equals("Book2"))
                .allMatch(b -> !isEmpty(b.getGenre()))
                .allMatch(b -> !isEmpty(b.getAuthor()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("возвращать ожидаемый список книг для автора ")
    @Test
    void shouldReturnExpectedAuthorBooksCount() {
        Statistics statistics = new Statistics(em);

        List<Book> books = repository.findByAuthorId(EXISTING_AUTHOR_ID);

        int expectedNumberOfBooks = 1;
        int expectedQueriesCount = 1;
        assertThat(books).isNotNull().hasSize(expectedNumberOfBooks)
                .allMatch(b -> !isEmpty(b.getName()))
                .anyMatch(b -> b.getName().equals("Book1"))
                .allMatch(b -> !isEmpty(b.getGenre()))
                .allMatch(b -> !isEmpty(b.getAuthor()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(expectedQueriesCount);
    }

    @DisplayName("возвращать ожидаемый список книг для жанра ")
    @Test
    void shouldReturnExpectedGenreBooksCount() {
        Statistics statistics = new Statistics(em);

        List<Book> books = repository.findByGenreId(EXISTING_GENRE_ID);

        int expectedNumberOfBooks = 1;
        int expectedQueriesCount = 1;
        assertThat(books).isNotNull().hasSize(expectedNumberOfBooks)
                .allMatch(b -> !isEmpty(b.getName()))
                .anyMatch(b -> b.getName().equals("Book2"))
                .allMatch(b -> !isEmpty(b.getGenre()))
                .allMatch(b -> !isEmpty(b.getAuthor()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(expectedQueriesCount);
    }
}