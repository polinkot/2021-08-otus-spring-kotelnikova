package ru.otus.pk.spring.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.resilience.Utils;
import ru.otus.pk.spring.service.AuthorService;

import java.util.List;

import static ru.otus.pk.spring.resilience.Utils.EXCEPTIONS;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthorController {

    private final AuthorService service;
    private final CircuitBreaker circuitBreaker;

    @GetMapping("/authors")
    public List<Author> findAll() {
        return Decorators.ofSupplier(service::findAll)
                .withCircuitBreaker(circuitBreaker)
                .withFallback(EXCEPTIONS, Utils::authorsFallback)
                .decorate().get();
    }
}
