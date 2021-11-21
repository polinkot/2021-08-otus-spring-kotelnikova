package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.dto.CommentBookDto;
import ru.otus.pk.spring.dto.CommentDto;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с комментариями должен ")
@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceImplTest {

    private static final CommentBookDto COMMENT_BOOK_DTO = new CommentBookDto("507f191e810c19729de860ea", "Book1");
    private static final CommentDto COMMENT_DTO = new CommentDto("407f191e810c19729de860ea", "Comment1", COMMENT_BOOK_DTO);
    private static final Book BOOK = new Book(COMMENT_BOOK_DTO.getId(), COMMENT_BOOK_DTO.getName(), new ArrayList<>());
    private static final Comment COMMENT = dtoToComment(COMMENT_DTO);

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
        given(repository.getAll()).willReturn(List.of(COMMENT_DTO));

        List<CommentDto> actualList = service.getAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(COMMENT_DTO);
    }

    @DisplayName("возвращать ожидаемый комментарий по id")
    @Test
    void shouldReturnExpectedCommentById() {
        given(repository.getById(COMMENT_DTO.getId())).willReturn(Optional.of(COMMENT_DTO));

        CommentDto actualComment = service.getById(COMMENT_DTO.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(COMMENT_DTO);
    }

    @DisplayName("добавлять комментарий")
    @Test
    void shouldAddComment() {
        given(repository.save(any(Comment.class))).willReturn(COMMENT);
        given(bookRepository.findById(BOOK.getId())).willReturn(Optional.of(BOOK));
        given(repository.getById(COMMENT.getId())).willReturn(Optional.of(COMMENT_DTO));

        CommentDto actualComment = service.add(COMMENT.getText(), COMMENT_BOOK_DTO.getId());
        assertThat(actualComment).isEqualTo(COMMENT_DTO);
    }

    @DisplayName("редактировать комментарий")
    @Test
    void shouldUpdateComment() {
        given(repository.findById(COMMENT.getId())).willReturn(Optional.of(COMMENT));
        given(repository.getById(COMMENT.getId())).willReturn(Optional.of(COMMENT_DTO));
        given(repository.save(any(Comment.class))).willReturn(COMMENT);
        given(bookRepository.findFirstByCommentsId(COMMENT.getId())).willReturn(BOOK);

        CommentDto actualComment = service.edit(COMMENT.getId(), COMMENT.getText());
        assertThat(actualComment).isEqualTo(COMMENT_DTO);
    }

    @DisplayName("возвращать ожидаемый список комментариев для книги ")
    @Test
    void shouldReturnExpectedBookComments() {
        CommentDto comment1 = new CommentDto("407f191e810c19729de860ea", "Comment1", COMMENT_BOOK_DTO);
        CommentDto comment2 = new CommentDto("307f191e810c19729de860ea", "Comment2", COMMENT_BOOK_DTO);

        Book book = new Book(COMMENT_BOOK_DTO.getId(), COMMENT_BOOK_DTO.getName(),
                List.of(dtoToComment(comment1), dtoToComment(comment2)));

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        given(repository.getComments(Set.of(comment1.getId(), comment2.getId()))).willReturn(List.of(comment1, comment2));

        List<CommentDto> actualComments = service.findByBookId(book.getId());
        Assertions.assertThat(actualComments).usingFieldByFieldElementComparator().containsExactly(comment1, comment2);
    }

    private static Comment dtoToComment(CommentDto dto) {
        return new Comment(dto.getId(), dto.getText(), LocalDateTime.now());
    }
}