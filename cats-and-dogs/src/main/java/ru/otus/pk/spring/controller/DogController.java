package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Dog;
import ru.otus.pk.spring.service.DogService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class DogController {

    private final DogService service;

    @GetMapping("/dogs")
    public List<Dog> findAll() {
        return service.findAll();
    }
}
