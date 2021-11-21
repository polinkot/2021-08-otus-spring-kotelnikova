package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.apache.commons.collections4.ListUtils.union;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    private static final List<AggregationOperation> OPERATIONS = List.of(
            project().and("_id").as("_id")
                    .and("name").as("name"));

    @Override
    public List<GenreDto> getAll() {
        return mongoTemplate.aggregate(newAggregation(OPERATIONS), Genre.class, GenreDto.class).getMappedResults();
    }

    @Override
    public Optional<GenreDto> getById(String genreId) {
        return !isEmpty(genreId) ? Optional.of(getGenres(Set.of(genreId)).get(0)) : Optional.empty();
    }

    @Override
    public List<GenreDto> getGenres(Set<String> genreIds) {
        List<MatchOperation> filterByGenre = singletonList(match(Criteria.where("_id").in(genreIds)));

        return mongoTemplate
                .aggregate(newAggregation(union(filterByGenre, OPERATIONS)), Genre.class, GenreDto.class).getMappedResults();
    }
}
