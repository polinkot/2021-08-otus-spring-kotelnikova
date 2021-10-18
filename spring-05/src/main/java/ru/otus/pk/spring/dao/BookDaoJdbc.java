package ru.otus.pk.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.domain.Book;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    public static final RowMapper<Book> ROW_MAPPER = new BeanPropertyRowMapper<>(Book.class);

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from book", emptyMap(), Integer.class);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id, name, author_id, genre_id from book", ROW_MAPPER);
    }

    @Override
    public Book getById(Long id) {
        return jdbc.queryForObject("select * from book where id = :id", Map.of("id", id), ROW_MAPPER);
    }

    @Override
    public Long insert(Book book) {
        SqlParameterSource params = new MapSqlParameterSource(Map.of(
                "name", book.getName(), "authorId", book.getAuthorId(), "genreId", book.getGenreId()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into book (`name`, author_id, genre_id) values (:name, :authorId, :genreId)",
                params, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Book book) {
        return jdbc.update("update book set `name` = :name, author_id = :authorId, genre_id = :genreId where id = :id",
                Map.of("id", book.getId(), "name", book.getName(),
                        "authorId", book.getAuthorId(), "genreId", book.getGenreId()));
    }

    @Override
    public int deleteById(Long id) {
        return jdbc.update("delete from book where id = :id", Map.of("id", id));
    }
}