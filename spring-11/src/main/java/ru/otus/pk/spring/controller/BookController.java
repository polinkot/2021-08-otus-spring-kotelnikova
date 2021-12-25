package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.repository.BookRepository;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookRepository repository;

    @GetMapping("/book")
    public Flux<Book> all() {
        return repository.findAll();
    }

    @GetMapping("/book/{id}")
    public Mono<Book> byId(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/book")
    public Mono<Book> save(@RequestBody Mono<Book> dto) {
        return repository.save(dto);
    }
}
