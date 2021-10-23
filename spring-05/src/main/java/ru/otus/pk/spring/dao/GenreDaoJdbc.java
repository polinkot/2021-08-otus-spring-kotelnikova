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
import ru.otus.pk.spring.domain.Genre;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    public static final RowMapper<Genre> GENRE_ROW_MAPPER = new BeanPropertyRowMapper<>(Genre.class);

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from genre", emptyMap(), Integer.class);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genre", GENRE_ROW_MAPPER);
    }

    @Override
    public Genre getById(Long id) {
        return jdbc.queryForObject("select * from genre where id = :id", Map.of("id", id), GENRE_ROW_MAPPER);
    }

    @Override
    public Long insert(Genre genre) {
        SqlParameterSource params = new MapSqlParameterSource(Map.of("name", genre.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genre (name) values (:name)", params, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Genre genre) {
        return jdbc.update("update genre set name = :name where id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public int deleteById(Long id) {
        return jdbc.update("delete from genre where id = :id", Map.of("id", id));
    }
}
