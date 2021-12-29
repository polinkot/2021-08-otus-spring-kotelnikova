package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.repository.BookRepository;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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

    @RequestMapping(value = "/books", method = {POST, PUT})
    public Mono<Book> save(@RequestBody Book book) {
        return repository.save(book);
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable("id") String id) {
        repository.deleteById(id).subscribe();
    }
}
