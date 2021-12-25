package ru.otus.pk.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.pk.spring.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findAll();

    Mono<Book> findById(String id);

    Mono<Book> save(Mono<Book> book);
}
