package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Comment;
import ru.otus.pk.spring.model.Genre;
import ru.otus.pk.spring.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с комментариями должен ")
@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceImplTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final Comment COMMENT = new Comment(1L, "Comment1", BOOK);

    @MockBean
    private CommentRepository repository;
    @MockBean
    private BookService bookService;

    @Autowired
    private CommentServiceImpl service;

    @DisplayName("возвращать ожидаемый список комментариев")
    @Test
    void shouldReturnExpectedCommentsList() {
        given(repository.findAll()).willReturn(List.of(COMMENT));

        List<Comment> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(COMMENT);
    }

    @DisplayName("возвращать ожидаемое количество комментариев")
    @Test
    void shouldReturnExpectedCommentCount() {
        long expectedCount = 2;

        given(repository.count()).willReturn(expectedCount);

        long actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый комментарий по id")
    @Test
    void shouldReturnExpectedCommentById() {
        given(repository.findById(COMMENT.getId())).willReturn(Optional.of(COMMENT));

        Comment actualComment = service.findById(COMMENT.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(COMMENT);
    }

//    @DisplayName("добавлять комментарий")
//    @Test
//    void shouldInsertComment() {
//        String commentText = "newComment";
//        String bookName = "newBook";
//        String authorFirstName = "newAuthorF";
//        String authorLastName = "newAuthorL";
//        String genreName = "newGenre";
//
//        Book newBook = new Book(null, bookName,
//                new Author(null, authorFirstName, authorLastName),
//                new Genre(null, genreName));
//        Comment newComment = new Comment(100L, commentText, newBook);
//
//        given(repository.save(any(Comment.class))).willReturn(newComment);
//        given(bookService.findByIdOrCreateNew(anyLong(), anyString(), anyLong(), anyString(), anyString(), anyLong(), anyString()))
//                .willReturn(newBook);
//
//        Comment actualComment = service.save(null, commentText, null, bookName,
//                null, authorFirstName, authorLastName, null, genreName);
//        assertThat(actualComment).isEqualTo(newComment);
//    }
//
//    @DisplayName("редактировать комментарий")
//    @Test
//    void shouldUpdateComment() {
//        String changedText = "changedName";
//        Comment changedComment = new Comment(COMMENT.getId(), changedText, COMMENT.getBook());
//
//        given(repository.findById(COMMENT.getId())).willReturn(Optional.of(COMMENT));
//        given(repository.save(any(Comment.class))).willReturn(changedComment);
//        given(bookService.findByIdOrCreateNew(anyLong(), anyString(), anyLong(), anyString(), anyString(), anyLong(), anyString()))
//                .willReturn(BOOK);
//
//        Comment actualComment = service.save(1L, changedText);
//        assertThat(actualComment).isEqualTo(changedComment);
//    }

    @DisplayName("удалять заданный комментарий по id")
    @Test
    void shouldCorrectlyDeleteCommentById() {
        Long id = 1L;
        int expectedDeletedCount = 1;

        given(repository.deleteById(id)).willReturn(expectedDeletedCount);

        int actualDeletedCount = service.deleteById(id);
        assertThat(actualDeletedCount).isEqualTo(expectedDeletedCount);
    }
}