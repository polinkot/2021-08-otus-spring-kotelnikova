package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с комментариями должен ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    private static final Long EXISTING_COMMENT_ID = 1L;

    public static final Long EXISTING_BOOK_ID = 1L;

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private CommentRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружать коментарий по id")
    @Test
    void shouldFindExpectedCommentById() {
        val actualComment = repository.findById(EXISTING_COMMENT_ID);
        val expectedComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("загружать список всех комментариев")
    @Test
    void findAll() {
        Statistics statistics = new Statistics(em);
        statistics.setStatisticsEnabled(true);

        val comments = repository.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> !isEmpty(c.getText()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);

        String text = "newComment";
        Comment savedComment = repository.save(new Comment(null, text, existingBook));

        Comment actualComment = em.find(Comment.class, savedComment.getId());
        assertThat(actualComment).isNotNull();
        assertThat(actualComment).extracting("text", "book")
                .doesNotContainNull()
                .containsExactly(text, existingBook);
    }

    @DisplayName("редактировать комментарий в БД")
    @Test
    void shouldUpdateComment() {
        Comment existingComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        existingComment.setText("updatedText");
        Comment savedComment = repository.save(existingComment);

        Comment actualComment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(savedComment);
    }

    @DisplayName("удалять комментарий по id")
    @Test
    void shouldCorrectyDeleteCommentById() {
        Comment comment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(comment).isNotNull();

        repository.deleteById(EXISTING_COMMENT_ID);
        em.detach(comment);

        comment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(comment).isNull();
    }
}