package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Set<Author> findAllAuthors() {
        List<AggregationOperation> operations = List.of(
                project().and("author.id").as("_id")
                        .and("author.firstName").as("firstName")
                        .and("author.lastName").as("lastName"));

        return new HashSet<>(mongoTemplate
                .aggregate(newAggregation(operations), Book.class, Author.class)
                .getMappedResults());
    }

    @Override
    public Set<Genre> findAllGenres() {
        List<AggregationOperation> operations = List.of(
                project().and("genre.id").as("_id")
                        .and("genre.name").as("name"));

        return new HashSet<>(mongoTemplate
                .aggregate(newAggregation(operations), Book.class, Genre.class)
                .getMappedResults());
    }
}
