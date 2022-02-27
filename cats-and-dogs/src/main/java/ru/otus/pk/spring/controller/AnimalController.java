package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Animal;
import ru.otus.pk.spring.service.AnimalService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AnimalController {

    private final AnimalService service;

    @GetMapping("/animals")
    public List<Animal> findAll() {
        return service.findAll();
    }

    @GetMapping("/animals/{id}")
    public Animal findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/animals")
    public Animal create(@RequestBody Animal animal) {
        return service.save(animal);
    }

    @PutMapping("/animals")
    public Animal update(@RequestBody Animal animal) {
        return service.save(animal);
    }

    @DeleteMapping("/animals/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
