package ru.otus.pk.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.pk.spring.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
