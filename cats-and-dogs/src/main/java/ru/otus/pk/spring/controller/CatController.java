package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Cat;
import ru.otus.pk.spring.service.CatService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CatController {

    private final CatService service;

    @GetMapping("/cats")
    public List<Cat> findAll() {
        return service.findAll();
    }
}
