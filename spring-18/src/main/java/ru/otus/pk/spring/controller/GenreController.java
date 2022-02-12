package ru.otus.pk.spring.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.resilience.Utils;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

import static ru.otus.pk.spring.resilience.Utils.EXCEPTIONS;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class GenreController {

    private final GenreService service;
    private final CircuitBreaker genreCircuitBreaker;

    @GetMapping("/genres")
    public List<Genre> finAll() {
        return Decorators.ofSupplier(service::findAll)
                .withCircuitBreaker(genreCircuitBreaker)
                .withFallback(EXCEPTIONS, Utils::genresFallback)
                .decorate().get();
    }
}
