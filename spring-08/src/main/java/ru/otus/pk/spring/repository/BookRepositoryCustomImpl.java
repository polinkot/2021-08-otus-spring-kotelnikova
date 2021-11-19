package ru.otus.pk.spring.repository;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.BookDto;
import ru.otus.pk.spring.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

//    @Override
//    public List<BookDto> getAll() {
//        val aggregation = newAggregation(project().and("_id").as("id").and("name").as("name"));
//        return mongoTemplate.aggregate(aggregation, Book.class, BookDto.class).getMappedResults();
//    }

    @Override
    public List<BookDto> getAll() {
//        var aggregation = newAggregation(
//                unwind("books"),
//                project().and("books.id").as("_id")
//                        .and("books.name").as("name")
//                        .and("id").as("author._id")
//                        .and("firstName").as("author.firstName")
//                        .and("lastName").as("author.lastName"));
//        List<BookDto> books = mongoTemplate.aggregate(aggregation, Author.class, BookDto.class).getMappedResults();
//
//        aggregation = newAggregation(
//                unwind("books"),
//                project().and("books.id").as("_id")
//                        .and("books.name").as("name")
//                        .and("id").as("genre._id")
//                        .and("name").as("genre.name"));
//        Map<String, GenreDto> genreBooks = mongoTemplate.aggregate(aggregation, Genre.class, BookDto.class).getMappedResults()
//                .stream().collect(toMap(BookDto::getId, BookDto::getGenre));
//
//        books.forEach(book -> book.setGenre(genreBooks.get(book.getId())));
//
//        return books;

        return getBooks(null);
    }

    @Override
    public Optional<BookDto> getById(String bookId) {
        return !isEmpty(bookId) ? Optional.of(getBooks(bookId).get(0)) : Optional.empty();
    }

    private List<BookDto> getBooks(String bookId) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        if (bookId != null) {
            aggregationOperations.add(match(Criteria.where("books.id").is(bookId)));
        }

        aggregationOperations.addAll(List.of(
                unwind("books"),
                project().and("books.id").as("_id")
                        .and("books.name").as("name")
                        .and("id").as("author._id")
                        .and("firstName").as("author.firstName")
                        .and("lastName").as("author.lastName")));

        var aggregation = newAggregation(aggregationOperations);
        List<BookDto> books = mongoTemplate.aggregate(aggregation, Author.class, BookDto.class).getMappedResults();

        aggregationOperations = new ArrayList<>();
        if (bookId != null) {
            aggregationOperations.add(match(Criteria.where("books.id").is(bookId)));
        }

        aggregationOperations.addAll(List.of(
                unwind("books"),
                project().and("books.id").as("_id")
                        .and("books.name").as("name")
                        .and("id").as("genre._id")
                        .and("name").as("genre.name")));

        aggregation = newAggregation(aggregationOperations);
        Map<String, GenreDto> genreBooks = mongoTemplate.aggregate(aggregation, Genre.class, BookDto.class).getMappedResults()
                .stream().collect(toMap(BookDto::getId, BookDto::getGenre));

        if (bookId != null) {
            books = books.stream().filter(book -> book.getId().equals(bookId)).collect(toList());
        }

        books.forEach(book -> book.setGenre(genreBooks.get(book.getId())));

        return books;
    }
}
