package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.mongodomain.Book2;

import java.util.List;

@Service
public class CleanUpService {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void cleanUp() throws Exception {
        List<Book2> books = jdbcTemplate.query("select * from book ", new BeanPropertyRowMapper<>(Book2.class));
        System.out.println("********** after cleanUp****************");
        System.out.println(books);

        List<Author> authors = jdbcTemplate.query("select * from author join mongo_author on author_id = id ", new BeanPropertyRowMapper<>(Author.class));
        System.out.println(authors);

        List<Genre> genres = jdbcTemplate.query("select * from genre join mongo_genre on genre_id = id ", new BeanPropertyRowMapper<>(Genre.class));
        System.out.println(genres);

        System.out.println("Выполняю завершающие мероприятия...");
        Thread.sleep(1000);
        System.out.println("Завершающие мероприятия закончены");
    }
}





