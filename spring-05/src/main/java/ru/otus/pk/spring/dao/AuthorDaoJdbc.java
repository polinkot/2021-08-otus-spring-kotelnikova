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
import ru.otus.pk.spring.domain.Author;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    public static final RowMapper<Author> ROW_MAPPER = new BeanPropertyRowMapper<>(Author.class);

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from author", emptyMap(), Integer.class);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, first_name, last_name from author", ROW_MAPPER);
    }

    @Override
    public Author getById(Long id) {
        return jdbc.queryForObject("select * from author where id = :id", Map.of("id", id), ROW_MAPPER);
    }

    @Override
    public Long insert(Author author) {
        SqlParameterSource params = new MapSqlParameterSource(Map.of(
                "firstName", author.getFirstName(), "lastName", author.getLastName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into author (first_name, last_name) values (:firstName, :lastName)", params, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Author author) {
        return jdbc.update("update author set first_name = :firstName, last_name = :lastName where id = :id",
                Map.of("id", author.getId(),
                        "firstName", author.getFirstName(), "lastName", author.getLastName()));
    }

    @Override
    public int deleteById(Long id) {
        return jdbc.update("delete from author where id = :id", Map.of("id", id));
    }
}
