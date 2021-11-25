package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.pk.spring.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с комментариями должен ")
@DataMongoTest
class CommentRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 3;

//    @Autowired
//    private CommentRepository repository;
//
//    @DisplayName("загружать дто комментария по id")
//    @Test
//    void shouldGetExpectedCommentById() {
//        val all = repository.findAll();
//        Comment comment = all.get(0);
//
//        val actualComment = repository.getById(comment.getId());
//        assertThat(actualComment).isPresent().get()
//                .hasFieldOrPropertyWithValue("text", comment.getText());
//    }
//
//    @DisplayName("загружать список дто комментариев")
//    @Test
//    void getAll() {
//        val comments = repository.getAll();
//        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
//                .allMatch(c -> !isEmpty(c.getText()));
//    }
}