package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.model.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final long ACTUAL_BOOK_ID = 1L;

    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной книге по id")
    @Test
    void shouldFindExpectedBookById() {
        val actualBook = repositoryJpa.findById(ACTUAL_BOOK_ID);
        System.out.println(actualBook);
        val expectedBook = em.find(Book.class, ACTUAL_BOOK_ID);
        System.out.println(expectedBook);
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }
}