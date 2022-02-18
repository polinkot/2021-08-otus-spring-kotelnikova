package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Adoption;
import ru.otus.pk.spring.service.AdoptionService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AdoptionController {

    private final AdoptionService service;

    @GetMapping("/adoptions")
    public List<Adoption> findAll() {
        return service.findAll();
    }
}
