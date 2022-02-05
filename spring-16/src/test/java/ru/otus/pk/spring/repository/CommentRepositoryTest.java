package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.pk.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с комментариями должен ")
@DataJpaTest
@AutoConfigureTestDatabase(connection = H2, replace = AUTO_CONFIGURED)
class CommentRepositoryTest {

    private static final Long EXISTING_COMMENT_ID = 1L;

    public static final Long EXISTING_BOOK_ID = 1L;

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private CommentRepository repository;

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

        val comments = repository.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(c -> !isEmpty(c.getText()))
                .allMatch(c -> !isEmpty(c.getTime()))
                .anyMatch(c -> c.getText().equals("Comment1"))
                .anyMatch(c -> c.getText().equals("Comment2"))
                .allMatch(c -> !isEmpty(c.getBook()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("возвращать ожидаемый список комментариев для книги ")
    @Test
    void shouldReturnExpectedBookCommentsCount() {
        Statistics statistics = new Statistics(em);

        List<Comment> comments = repository.findByBookId(EXISTING_BOOK_ID);

        int expectedNumberOfComments = 2;
        int expectedQueriesCount = 1;
        assertThat(comments).isNotNull().hasSize(expectedNumberOfComments)
                .allMatch(c -> !isEmpty(c.getText()))
                .allMatch(c -> !isEmpty(c.getTime()))
                .anyMatch(c -> c.getText().equals("Comment1"))
                .anyMatch(c -> c.getText().equals("Comment2"))
                .allMatch(c -> !isEmpty(c.getBook()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(expectedQueriesCount);
    }
}