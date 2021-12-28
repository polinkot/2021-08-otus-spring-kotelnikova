package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import ru.otus.pk.spring.domain.*;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Author> findAllAuthors() {
        List<AggregationOperation> operations = List.of(
                group("author.id", "author.firstName", "author.lastName")
                , project().and("id").as("_id")
                        .and("firstName").as("firstName")
                        .and("lastName").as("lastName")
        );

        return mongoTemplate.aggregate(newAggregation(operations), Book.class, Author.class).getMappedResults();
    }

    @Override
    public List<Genre> findAllGenres() {
        List<AggregationOperation> operations = List.of(
                group("genre.id", "genre.name")
                , project().and("id").as("_id")
                        .and("name").as("name")
        );

        return mongoTemplate.aggregate(newAggregation(operations), Book.class, Genre.class).getMappedResults();
    }
}
