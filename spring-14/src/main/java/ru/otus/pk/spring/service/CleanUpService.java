package ru.otus.pk.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.mongodomain.*;
import ru.otus.pk.spring.repository.AuthorRepository;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.util.Map.ofEntries;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Service
public class CleanUpService {

//    @Autowired
//    EntityManager entityManager;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void cleanUp() throws Exception {
//        List authors = entityManager.createQuery("Select t from " + Author.class + " t").getResultList();
//        System.out.println("***********CleanUpService************authors");
//        System.out.println(authors);

//        jdbcTemplate.execute("update book set author_id = 2");


        List<Book2> books = jdbcTemplate.query("select * from book ", new BeanPropertyRowMapper<>(Book2.class));
        System.out.println("********** after cleanUp****************");
        System.out.println(books);

        List<Author> authors = jdbcTemplate.query("select * from author join mongo_author on author_id = id ", new BeanPropertyRowMapper<>(Author.class));
        System.out.println(authors);

        List<Genre> genres = jdbcTemplate.query("select * from genre ", new BeanPropertyRowMapper<>(Genre.class));
        System.out.println(genres);

//        List<Object[]> allAsMap = authorRepository.findAllAsMap();
//        System.out.println(allAsMap);

//        Map<String, Author> map = authorRepository.findByMongoIds(List.of(authors.get(0).getMongoId()));
//        Map<String, Author> map = authorRepository.findByMongoIdIn(List.of(authors.get(0).getMongoId()))
//                .collect(toMap(Author::getMongoId, identity()));
//        Map<String, List<Author>> map = authorRepository.findByMongoIdIn(List.of(authors.get(0).getMongoId()))
//                .collect(groupingBy(Author::getMongoId, toList()));
//        System.out.println(map);

//        Map<String, Author> map1 = ofEntries(authorRepository.findByMongoIds().toArray(Entry[]::new));
//        System.out.println(map1);





        System.out.println("Выполняю завершающие мероприятия...");
        Thread.sleep(1000);
        System.out.println("Завершающие мероприятия закончены");
    }
}





