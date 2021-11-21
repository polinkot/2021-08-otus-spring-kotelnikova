package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.dto.AuthorDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.apache.commons.collections4.ListUtils.union;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    private static final List<AggregationOperation> OPERATIONS = List.of(
            project().and("_id").as("_id")
                    .and("firstName").as("firstName")
                    .and("lastName").as("lastName"));

    @Override
    public List<AuthorDto> getAll() {
        return mongoTemplate.aggregate(newAggregation(OPERATIONS), Author.class, AuthorDto.class).getMappedResults();
    }

    @Override
    public Optional<AuthorDto> getById(String authorId) {
        return !isEmpty(authorId) ? Optional.of(getAuthors(Set.of(authorId)).get(0)) : Optional.empty();
    }

    @Override
    public List<AuthorDto> getAuthors(Set<String> authorIds) {
        List<MatchOperation> filterByAuthor = singletonList(match(Criteria.where("_id").in(authorIds)));

        return mongoTemplate
                .aggregate(newAggregation(union(filterByAuthor, OPERATIONS)), Author.class, AuthorDto.class).getMappedResults();
    }
}
