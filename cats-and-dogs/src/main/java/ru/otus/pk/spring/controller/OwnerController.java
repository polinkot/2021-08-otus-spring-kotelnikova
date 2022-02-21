package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Owner;
import ru.otus.pk.spring.service.OwnerService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class OwnerController {

    private final OwnerService service;

    @GetMapping("/owners")
    public List<Owner> findAll() {
        return service.findAll();
    }

    @GetMapping("/owners/{id}")
    public Owner findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/owners")
    public Owner create(@RequestBody Owner owner) {
        return service.save(owner);
    }

    @PutMapping("/owners")
    public Owner update(@RequestBody Owner owner) {
        return service.save(owner);
    }

    @DeleteMapping("/owners/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
