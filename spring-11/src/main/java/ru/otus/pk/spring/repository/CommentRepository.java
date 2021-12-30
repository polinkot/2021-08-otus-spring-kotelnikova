package ru.otus.pk.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.pk.spring.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findByBookId(String bookId);
}
