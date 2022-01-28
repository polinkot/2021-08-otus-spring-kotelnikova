package ru.otus.pk.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.pk.spring.domain.Book;

import java.util.Collection;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {

    Set<Book> findByMongoIdIn(@Param("mongoIds") Collection<String> mongoIds);
}
