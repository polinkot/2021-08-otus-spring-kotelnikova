package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final BookRepository repository;

    @GetMapping("/genres")
    public List<Genre> finAll() {
        return repository.findAllGenres();
    }
}
