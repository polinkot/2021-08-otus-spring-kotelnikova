package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @Autowired
    private CommentServiceImpl service;

    @DisplayName("возвращать ожидаемое количество комментариев")
    @Test
    void shouldReturnExpectedCommentCount() {
        Long expectedCount = 2L;

        given(repository.count()).willReturn(expectedCount);

        Long actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список комментариев")
    @Test
    void shouldReturnExpectedCommentsList() {
        given(repository.findAll()).willReturn(List.of(COMMENT));

        List<Comment> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(COMMENT);
    }

    @DisplayName("возвращать ожидаемый комментарий по id")
    @Test
    void shouldReturnExpectedCommentById() {
        given(repository.findById(COMMENT.getId())).willReturn(Optional.of(COMMENT));

        Comment actualComment = service.findById(COMMENT.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(COMMENT);
    }

    @DisplayName("добавлять комментарий")
    @Test
    void shouldAddComment() {
        given(repository.save(any(Comment.class))).willReturn(COMMENT);

        Comment actualComment = service.save(new Comment(10L, "comment1", BOOK));
        assertThat(actualComment).isEqualTo(COMMENT);
    }

    @DisplayName("редактировать комментарий")
    @Test
    void shouldUpdateComment() {
        given(repository.findById(COMMENT.getId())).willReturn(Optional.of(COMMENT));
        given(repository.save(any(Comment.class))).willReturn(COMMENT);

        Comment actualComment = service.save(COMMENT);
        assertThat(actualComment).isEqualTo(COMMENT);
    }

    @DisplayName("возвращать ожидаемый список комментариев для книги ")
    @Test
    void shouldReturnExpectedBookCommentsCount() {
        Comment comment1 = new Comment(1L, "Comment1", BOOK);
        Comment comment2 = new Comment(1L, "Comment2", BOOK);

        given(repository.findByBookId(BOOK.getId())).willReturn(List.of(comment1, comment2));

        List<Comment> actualComments = service.findByBookId(BOOK.getId());
        Assertions.assertThat(actualComments).usingFieldByFieldElementComparator().containsExactly(comment1, comment2);
    }
}