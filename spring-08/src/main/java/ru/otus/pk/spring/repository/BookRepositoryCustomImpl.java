package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.BookDto;
import ru.otus.pk.spring.dto.GenreDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.collections4.ListUtils.union;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    private static final List<AggregationOperation> AUTHOR_OPERATIONS = List.of(
            unwind("books"),
            project().and("books.id").as("_id")
                    .and("books.name").as("name")
                    .and("id").as("author._id")
                    .and("firstName").as("author.firstName")
                    .and("lastName").as("author.lastName"));

    public static final List<AggregationOperation> GENRE_OPERATIONS = List.of(
            unwind("books"),
            project().and("books.id").as("_id")
                    .and("books.name").as("name")
                    .and("id").as("genre._id")
                    .and("name").as("genre.name"));

    @Override
    public List<BookDto> getAll() {
        Map<String, GenreDto> genreBooks = mongoTemplate
                .aggregate(newAggregation(GENRE_OPERATIONS), Genre.class, BookDto.class)
                .getMappedResults().stream()
                .collect(toMap(BookDto::getId, BookDto::getGenre));

        return mongoTemplate.aggregate(newAggregation(AUTHOR_OPERATIONS), Author.class, BookDto.class)
                .getMappedResults().stream()
                .peek(book -> book.setGenre(genreBooks.get(book.getId())))
                .collect(toList());
    }

    @Override
    public Optional<BookDto> getById(String bookId) {
        return !isEmpty(bookId) ? Optional.of(getBooks(Set.of(bookId)).get(0)) : Optional.empty();
    }

    @Override
    public List<BookDto> getBooks(Set<String> bookIds) {
        List<MatchOperation> filterByBook = singletonList(match(Criteria.where("books.id").in(bookIds)));

        Map<String, GenreDto> genreBooks = mongoTemplate
                .aggregate(newAggregation(union(filterByBook, GENRE_OPERATIONS)), Genre.class, BookDto.class)
                .getMappedResults().stream()
                .collect(toMap(BookDto::getId, BookDto::getGenre));

        return mongoTemplate
                .aggregate(newAggregation(union(filterByBook, AUTHOR_OPERATIONS)), Author.class, BookDto.class)
                .getMappedResults().stream()
                .filter(book -> bookIds.contains(book.getId()))
                .peek(book -> book.setGenre(genreBooks.get(book.getId())))
                .collect(toList());
    }
}
