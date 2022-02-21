package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Dog;
import ru.otus.pk.spring.service.DogService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class DogController {

    private final DogService service;

    @GetMapping("/dogs")
    public List<Dog> findAll() {
        return service.findAll();
    }

    @GetMapping("/dogs/{id}")
    public Dog findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/dogs")
    public Dog create(@RequestBody Dog dog) {
        return service.save(dog);
    }

    @PutMapping(value = "/dogs")
    public Dog update(@RequestBody Dog dog) {
        return service.save(dog);
    }

    @DeleteMapping("/dogs/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
