package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.controller.dto.AdoptionDto;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.service.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AdoptionController {

    private final AdoptionService service;
    private final OwnerService ownerService;
    private final AnimalService animalService;

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
    public Adoption create(@RequestBody AdoptionDto dto) {
        Owner owner = ownerService.findById(dto.getOwnerId());
        Animal animal = animalService.findById(dto.getAnimalId());
        Adoption adoption = new Adoption(null, animal, owner);
        return service.save(adoption);
    }

    @DeleteMapping("/adoptions/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
