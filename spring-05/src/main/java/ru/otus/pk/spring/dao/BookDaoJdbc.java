package ru.otus.pk.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    public static final RowMapper<Book> BOOK_ROW_MAPPER = new BookMapper();

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from book", emptyMap(), Integer.class);
    }

    @Override
    public List<Book> getAll() {
        String sql = "select b.id as book_id, b.name as book_name, " +
                "                   a.id as author_id, a.first_name as author_first_name, a.last_name as author_last_name, " +
                "                   g.id as genre_id, g.name as genre_name " +
                "          from book b " +
                "               left join  author a  on a.id = b.author_id " +
                "               left join  genre g  on g.id = b.genre_id";
        return jdbc.query(sql, BOOK_ROW_MAPPER);
    }

    @Override
    public Book getById(Long id) {
        String sql = "select b.id as book_id, b.name as book_name, " +
                "                   a.id as author_id, a.first_name as author_first_name, a.last_name as author_last_name, " +
                "                   g.id as genre_id, g.name as genre_name " +
                "          from book b " +
                "               left join  author a  on a.id = b.author_id " +
                "               left join  genre g  on g.id = b.genre_id " +
                "           where b.id = :id";

        return jdbc.queryForObject(sql, Map.of("id", id), BOOK_ROW_MAPPER);
    }

    @Override
    public Long insert(Book book) {
        SqlParameterSource params = new MapSqlParameterSource(Map.of(
                "name", book.getName(), "authorId", book.getAuthor().getId(), "genreId", book.getGenre().getId()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into book (`name`, author_id, genre_id) values (:name, :authorId, :genreId)",
                params, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Book book) {
        return jdbc.update("update book set `name` = :name, author_id = :authorId, genre_id = :genreId where id = :id",
                Map.of("id", book.getId(), "name", book.getName(),
                        "authorId", book.getAuthor().getId(), "genreId", book.getGenre().getId()));
    }

    @Override
    public int deleteById(Long id) {
        return jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = new Author(resultSet.getLong("author_id"),
                    resultSet.getString("author_first_name"),
                    resultSet.getString("author_last_name"));

            Genre genre = new Genre(resultSet.getLong("genre_id"),
                    resultSet.getString("genre_name"));

            Book book = new Book(resultSet.getLong("book_id"),
                    resultSet.getString("book_name"),
                    author,
                    genre);

            return book;
        }
    }
}