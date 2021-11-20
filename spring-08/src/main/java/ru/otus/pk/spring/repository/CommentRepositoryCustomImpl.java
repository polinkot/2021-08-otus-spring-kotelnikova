package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.dto.CommentDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.ListUtils.union;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    private static final List<AggregationOperation> BOOK_OPERATIONS = List.of(
            unwind("comments"),
            project().and("comments.id").as("_id")
                    .and("comments.text").as("text")
                    .and("id").as("book._id")
                    .and("name").as("book.name"));

    @Override
    public List<CommentDto> getAll() {
        return mongoTemplate.aggregate(newAggregation(BOOK_OPERATIONS), Book.class, CommentDto.class).getMappedResults();
    }

    @Override
    public Optional<CommentDto> getById(String commentId) {
        return !isEmpty(commentId) ? Optional.of(getComments(Set.of(commentId)).get(0)) : Optional.empty();
    }

    @Override
    public List<CommentDto> getComments(Set<String> commentIds) {
        List<MatchOperation> filterByComment = singletonList(match(Criteria.where("comments.id").in(commentIds)));

        return mongoTemplate
                .aggregate(newAggregation(union(filterByComment, BOOK_OPERATIONS)), Book.class, CommentDto.class)
                .getMappedResults().stream()
                .filter(comment -> commentIds.contains(comment.getId()))
                .collect(toList());
    }
}
