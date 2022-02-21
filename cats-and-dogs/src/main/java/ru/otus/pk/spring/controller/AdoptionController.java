package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Adoption;
import ru.otus.pk.spring.service.AdoptionService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AdoptionController {

    private final AdoptionService service;

    @GetMapping("/adoptions")
    public List<Adoption> findAll() {
        return service.findAll();
    }

    @GetMapping("/adoptions/{id}")
    public Adoption findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/adoptions")
    public Adoption create(@RequestBody Adoption adoption) {
        return service.save(adoption);
    }

    @DeleteMapping("/adoptions/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
