package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.repository.CommentRepository;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository repository;

//    @GetMapping("/comments")
//    public Flux<Comment> all() {
//        return repository.findAll();
//    }

//    @GetMapping("/comments/{id}")
//    public Mono<Comment> byId(@PathVariable("id") String id) {
//        return repository.findById(id);
//    }
//
//    @PostMapping("/comments")
//    public Mono<Comment> save(@RequestBody Comment comment) {
//        return repository.save(comment);
//    }

    @GetMapping("/books/{bookId}/comments")
    public Flux<Comment> findByBookId(@PathVariable("bookId") String bookId) {
        return repository.findByBookId(bookId);
    }
}
