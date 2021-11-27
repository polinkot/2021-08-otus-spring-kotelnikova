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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

    private static final Author AUTHOR = new Author(ObjectId.get().toString(), "AuthorF1", "AuthorL1");
    private static final Genre GENRE = new Genre(ObjectId.get().toString(), "Genre1");

    private static final Book BOOK1 = new Book(ObjectId.get().toString(), "Book1", AUTHOR, GENRE);
    private static final Book BOOK2 = new Book(ObjectId.get().toString(), "Book2", AUTHOR, GENRE);

    @MockBean
    private BookRepository repository;
    @MockBean
    private CommentService commentService;

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
        given(repository.findAll()).willReturn(List.of(BOOK1, BOOK2));

        List<Book> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(BOOK1, BOOK2);
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        given(repository.findById(BOOK1.getId())).willReturn(Optional.of(BOOK1));

        Book actualBook = service.findById(BOOK1.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK1);
    }

    @DisplayName("добавлять книгу")
    @Test
    void shouldAddBook() {
        given(repository.findFirstByAuthorId(AUTHOR.getId())).willReturn(Optional.of(BOOK1));
        given(repository.findFirstByGenreId(GENRE.getId())).willReturn(Optional.of(BOOK1));
        given(repository.save(any(Book.class))).willReturn(BOOK1);

        Book actualBook = service.add(BOOK1.getName(),
                AUTHOR.getId(), null, null,
                GENRE.getId(), null);
        assertThat(actualBook).isEqualTo(BOOK1);
    }

    @DisplayName("редактировать книгу")
    @Test
    void shouldUpdateBook() {
        Book updatedBook = new Book(ObjectId.get().toString(), "updatedBook", AUTHOR, GENRE);

        given(repository.findById(updatedBook.getId())).willReturn(Optional.of(updatedBook));
        given(repository.save(any(Book.class))).willReturn(updatedBook);

        Book actualBook = service.edit(updatedBook.getId(), updatedBook.getName());
        assertThat(actualBook).isEqualTo(updatedBook);
    }

    @DisplayName("возвращать ожидаемый список книг для автора ")
    @Test
    void shouldReturnExpectedAuthorBooks() {
        given(repository.findByAuthorId(AUTHOR.getId())).willReturn(List.of(BOOK1, BOOK2));

        List<Book> actualBooks = service.findByAuthorId(AUTHOR.getId());
        Assertions.assertThat(actualBooks).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(BOOK1, BOOK2);
    }

    @DisplayName("возвращать ожидаемый список книг для жанра ")
    @Test
    void shouldReturnExpectedGenreBooks() {
        given(repository.findByGenreId(GENRE.getId())).willReturn(List.of(BOOK1, BOOK2));

        List<Book> actualBooks = service.findByGenreId(GENRE.getId());
        Assertions.assertThat(actualBooks).usingFieldByFieldElementComparator().containsExactly(BOOK1, BOOK2);
    }
}