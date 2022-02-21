package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Cat;
import ru.otus.pk.spring.service.CatService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CatController {

    private final CatService service;

    @GetMapping("/cats")
    public List<Cat> findAll() {
        return service.findAll();
    }

    @GetMapping("/cats/{id}")
    public Cat findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/cats")
    public Cat create(@RequestBody Cat cat) {
        return service.save(cat);
    }

    @PutMapping(value = "/cats")
    public Cat update(@RequestBody Cat cat) {
        return service.save(cat);
    }

    @DeleteMapping("/cats/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
