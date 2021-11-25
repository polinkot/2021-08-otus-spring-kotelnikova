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
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с комментариями должен ")
@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceImplTest {

    private static final Author AUTHOR = new Author(ObjectId.get().toString(), "AuthorF1", "AuthorL1");
    private static final Genre GENRE = new Genre(ObjectId.get().toString(), "Genre1");
    private static final Book BOOK = new Book(ObjectId.get().toString(), "Book1", AUTHOR, GENRE);

    private static final Comment COMMENT1 = new Comment(ObjectId.get().toString(), "Comment1", BOOK);
    private static final Comment COMMENT2 = new Comment(ObjectId.get().toString(), "Comment2", BOOK);

    @MockBean
    private CommentRepository repository;
    @MockBean
    private BookRepository bookRepository;

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
        given(repository.findAll()).willReturn(List.of(COMMENT1, COMMENT2));

        List<Comment> actualList = service.findAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(COMMENT1, COMMENT2);
    }

    @DisplayName("возвращать ожидаемый комментарий по id")
    @Test
    void shouldReturnExpectedCommentById() {
        given(repository.findById(COMMENT1.getId())).willReturn(Optional.of(COMMENT1));

        Comment actualComment = service.findById(COMMENT1.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(COMMENT1);
    }

    @DisplayName("добавлять комментарий")
    @Test
    void shouldAddComment() {
        given(repository.save(any(Comment.class))).willReturn(COMMENT1);
        given(bookRepository.findById(BOOK.getId())).willReturn(Optional.of(BOOK));

        Comment actualComment = service.add(COMMENT1.getText(), BOOK.getId());
        assertThat(actualComment).isEqualTo(COMMENT1);
    }

    @DisplayName("редактировать комментарий")
    @Test
    void shouldUpdateComment() {
        Comment updatedComment = new Comment(ObjectId.get().toString(), "updatedComment", BOOK);

        given(repository.findById(updatedComment.getId())).willReturn(Optional.of(updatedComment));
        given(repository.save(any(Comment.class))).willReturn(updatedComment);

        Comment actualComment = service.edit(updatedComment.getId(), updatedComment.getText());
        assertThat(actualComment).isEqualTo(updatedComment);
    }

    @DisplayName("возвращать ожидаемый список комментариев для книги ")
    @Test
    void shouldReturnExpectedBookComments() {
        given(repository.findByBookId(BOOK.getId())).willReturn(List.of(COMMENT1, COMMENT2));

        List<Comment> actualComments = service.findByBookId(BOOK.getId());
        Assertions.assertThat(actualComments).usingFieldByFieldElementComparator().containsExactly(COMMENT1, COMMENT2);
    }
}