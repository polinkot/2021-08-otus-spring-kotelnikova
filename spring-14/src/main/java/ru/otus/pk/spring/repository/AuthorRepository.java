package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import ru.otus.pk.spring.domain.Author;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Stream<Author> findByMongoIdIn(@Param("mongoIds") Collection<String> mongoIds);

//    default Map<String, Author> findByMongoIds(@Param("mongoIds") Collection<String> mongoIds) {
//        return findByMongoIdIn(mongoIds).collect(toMap(Author::getMongoId, identity()));
//    }

    @Query("SELECT a FROM Author AS a WHERE a.mongoId IN :mongoIds ")
    Set<Author> findByMongoIds(@Param("mongoIds") Collection<String> mongoIds);
//
//    @Query("SELECT a.mongoId AS key, a AS value FROM Author AS a WHERE a.mongoId IN :mongoIds ")
//    List<Entry<String, Author>> findByMongoIds(@Param("mongoIds") Collection<String> mongoIds);

}
