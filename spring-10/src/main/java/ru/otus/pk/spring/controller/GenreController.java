package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreService service;

    @GetMapping("/genres")
    public List<Genre> finAll() {
        return service.findAll();
    }
}
