package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.pk.spring.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataMongoTest
class BookRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    @Autowired
    private BookRepository repository;

    @DisplayName("загружать дто книги по id")
    @Test
    void shouldGetExpectedBookById() {
        val all = repository.findAll();
        Book book = all.get(0);

        val actualBook = repository.getById(book.getId());
        assertThat(actualBook).isPresent().get()
                .hasFieldOrPropertyWithValue("name", book.getName());
    }

    @DisplayName("загружать список дто книг")
    @Test
    void getAll() {
        val books = repository.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !isEmpty(b.getName()));
    }
}