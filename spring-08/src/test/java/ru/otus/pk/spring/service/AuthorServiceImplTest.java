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

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с авторами должен ")
@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    private static final Author AUTHOR1 = new Author(ObjectId.get().toString(), "AuthorF1", "AuthorL1");
    private static final Author AUTHOR2 = new Author(ObjectId.get().toString(), "AuthorF2", "AuthorL2");

    private static final Genre GENRE = new Genre(ObjectId.get().toString(), "Genre1");
    private static final Book BOOK = new Book(ObjectId.get().toString(), "Book1", AUTHOR1, GENRE);

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentService commentService;

    @Autowired
    private AuthorServiceImpl service;

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int expectedCount = 2;

        given(bookRepository.findAllAuthors()).willReturn(Set.of(AUTHOR1, AUTHOR2));

        Integer actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        given(bookRepository.findAllAuthors()).willReturn(Set.of(AUTHOR1, AUTHOR2));

        Set<Author> actualAuthors = service.findAll();
        Assertions.assertThat(actualAuthors).usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(AUTHOR1, AUTHOR2);
    }

    @DisplayName("возвращать ожидаемого автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        given(bookRepository.findFirstByAuthorId(AUTHOR1.getId())).willReturn(Optional.of(BOOK));

        Author actualAuthor = service.findById(AUTHOR1.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(AUTHOR1);
    }
}