package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.mongodomain.Book2;
import ru.otus.pk.spring.mongodomain.Comment2;

import java.util.List;

@Service
public class CleanUpService {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void cleanUp() throws Exception {
        System.out.println("********** after cleanUp****************");

        List<Book2> books = jdbcTemplate.query("select * from book join mongo_book on book_id = id ", new BeanPropertyRowMapper<>(Book2.class));
        System.out.println(books);

        List<Author> authors = jdbcTemplate.query("select * from author join mongo_author on author_id = id ", new BeanPropertyRowMapper<>(Author.class));
        System.out.println(authors);

        List<Genre> genres = jdbcTemplate.query("select * from genre join mongo_genre on genre_id = id ", new BeanPropertyRowMapper<>(Genre.class));
        System.out.println(genres);

        List<Comment2> comments = jdbcTemplate.query("select * from comment ", new BeanPropertyRowMapper<>(Comment2.class));
        System.out.println(comments);

        System.out.println("Выполняю завершающие мероприятия...");
        Thread.sleep(1000);
        System.out.println("Завершающие мероприятия закончены");
    }
}
