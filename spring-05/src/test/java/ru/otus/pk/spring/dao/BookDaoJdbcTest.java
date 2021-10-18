package ru.otus.pk.spring.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.pk.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    public static final Long EXISTING_BOOK_ID = 1L;
    public static final String EXISTING_BOOK_NAME = "Hobbit";
    public static final Long EXISTING_BOOK_AUTHOR_ID = 1L;
    public static final Long EXISTING_BOOK_GENRE_ID = 1L;
    public static final Book EXPECTED_BOOK = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME,
            EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);

    @Autowired
    private BookDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int expectedCount = 1;

        int actualCount = dao.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book book = new Book(null, "new book", 1L, 1L);
        Long id = dao.insert(book);

        Book actualBook = dao.getById(id);
        assertThat(actualBook).extracting("name", "authorId", "genreId")
                .doesNotContainNull()
                .containsExactly("new book", 1L, 1L);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> actualBookList = dao.getAll();
        Assertions.assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXPECTED_BOOK);
    }

    @DisplayName("возвращать ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = dao.getById(EXPECTED_BOOK.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXPECTED_BOOK);
    }

    @DisplayName("редактировать книгу в БД")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, "updated", 1L, 1L);
        dao.update(expectedBook);
        Book actualBook = dao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять заданную книгу по её id")
    @Test
    void shouldCorrectlyDeleteBookById() {
        assertThatCode(() -> dao.getById(EXISTING_BOOK_ID)).doesNotThrowAnyException();

        dao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> dao.getById(EXISTING_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}