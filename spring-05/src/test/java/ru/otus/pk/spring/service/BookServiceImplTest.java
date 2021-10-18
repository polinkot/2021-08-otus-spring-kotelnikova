package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.dao.AuthorDao;
import ru.otus.pk.spring.dao.BookDao;
import ru.otus.pk.spring.dao.GenreDao;
import ru.otus.pk.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс BookServiceImpl")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

    @MockBean
    private BookDao dao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private BookServiceImpl service;

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book book = new Book(1L, "book", 2L, 2L);

        given(dao.getAll()).willReturn(List.of(book));

        List<Book> actualList = service.getAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(book);
    }

    @DisplayName("возвращать ожидаемое количество книг")
    @Test
    void shouldReturnExpectedBookCount() {
        int expectedCount = 3;

        given(dao.count()).willReturn(expectedCount);

        int actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемую книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(1L, "book", 1L, 1L);

        given(dao.getById(expectedBook.getId())).willReturn(expectedBook);

        Book actualBook = service.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу")
    @Test
    void shouldInsertBook() {
        Long id = 1L;
        String name = "new book";
        Long authorId = 1L;
        Long genreId = 1L;

        given(dao.insert(any(Book.class))).willReturn(id);

        Long actualId = service.insert(name, authorId, genreId);
        assertThat(actualId).isEqualTo(id);
    }

    @DisplayName("редактировать книгу")
    @Test
    void shouldUpdateBook() {
        Long id = 1L;
        String name = "updated book";
        Long authorId = 1L;
        Long genreId = 1L;

        int expectedUpdatedCount = 1;
        given(dao.update(any(Book.class))).willReturn(expectedUpdatedCount);

        int actualUpdatedCount = service.update(id, name, authorId, genreId);
        assertThat(actualUpdatedCount).isEqualTo(expectedUpdatedCount);
    }

    @DisplayName("удалять заданную книгу по её id")
    @Test
    void shouldCorrectlyDeleteBookById() {
        Long id = 1L;
        int expectedDeletedCount = 1;

        given(dao.deleteById(id)).willReturn(expectedDeletedCount);

        int actualDeletedCount = service.deleteById(id);
        assertThat(actualDeletedCount).isEqualTo(expectedDeletedCount);
    }
}