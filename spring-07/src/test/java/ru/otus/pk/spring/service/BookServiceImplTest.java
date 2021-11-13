package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book EXPECTED_BOOK = new Book(1L, "Book1", AUTHOR, GENRE);

    @MockBean
    private BookRepository repository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @Autowired
    private BookServiceImpl service;

    @DisplayName("возвращать ожидаемое количество книг")
    @Test
    void shouldReturnExpectedBookCount() {
        long expectedCount = 2;

        given(repository.count()).willReturn(expectedCount);

        long actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBooksList() {
        given(repository.findAll()).willReturn(List.of(EXPECTED_BOOK));

        List<Book> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(EXPECTED_BOOK);
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        given(repository.findById(EXPECTED_BOOK.getId())).willReturn(Optional.of(EXPECTED_BOOK));

        Book actualBook = service.findById(EXPECTED_BOOK.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXPECTED_BOOK);
    }

    @DisplayName("добавлять книгу")
    @Test
    void shouldInsertBook() {
        given(repository.save(any(Book.class))).willReturn(EXPECTED_BOOK);
        given(authorService.findById(AUTHOR.getId())).willReturn(AUTHOR);
        given(genreService.findById(GENRE.getId())).willReturn(GENRE);

        Book actualBook = service.save(null, EXPECTED_BOOK.getName(),
                AUTHOR.getId(), null, null,
                GENRE.getId(), null);
        assertThat(actualBook).isEqualTo(EXPECTED_BOOK);
    }

    @DisplayName("редактировать книгу")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book(1L, "changedName", AUTHOR, GENRE);

        given(repository.findById(expectedBook.getId())).willReturn(Optional.of(expectedBook));
        given(repository.save(any(Book.class))).willReturn(expectedBook);

        Book actualBook = service.save(1L, expectedBook.getName(), null, null, null, null, null);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемый список книг для автора ")
    @Test
    void shouldReturnExpectedAuthorBooksCount() {
        Book book1 = new Book(1L, "Book1", AUTHOR, new Genre(1L, "Genre1"));
        Book book2 = new Book(2L, "Book2", AUTHOR, new Genre(2L, "Genre2"));

        given(repository.findByAuthorId(AUTHOR.getId())).willReturn(List.of(book1, book2));

        List<Book> actualBooks = service.findByAuthorId(AUTHOR.getId());
        Assertions.assertThat(actualBooks).usingFieldByFieldElementComparator().containsExactly(book1, book2);
    }

    @DisplayName("возвращать ожидаемый список книг для жанра ")
    @Test
    void shouldReturnExpectedGenreBooksCount() {
        Book book2 = new Book(2L, "Book2", new Author(1L, "AuthorF", "AuthorL"), GENRE);

        given(repository.findByGenreId(GENRE.getId())).willReturn(List.of(book2));

        List<Book> actualBooks = service.findByGenreId(GENRE.getId());
        Assertions.assertThat(actualBooks).usingFieldByFieldElementComparator().containsExactly(book2);
    }
}