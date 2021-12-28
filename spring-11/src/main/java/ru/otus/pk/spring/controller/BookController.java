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

    @GetMapping("/books")
    public Flux<Book> all() {
        return repository.findAll();
    }

    @GetMapping("/books/{id}")
    public Mono<Book> byId(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/books")
    public Mono<Book> save(@RequestBody Book book) {
        return repository.save(book);
    }

    @PutMapping(value = "/books")
    public void update(@RequestBody Book book) {
        repository.save(book).subscribe();
    }
}
