package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.repository.CommentRepository;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository repository;

    @GetMapping("/comment")
    public Flux<Comment> all() {
        return repository.findAll();
    }

    @GetMapping("/comment/{id}")
    public Mono<Comment> byId(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/comment")
    public Mono<Comment> save(@RequestBody Mono<Comment> dto) {
        return repository.save(dto);
    }
}
