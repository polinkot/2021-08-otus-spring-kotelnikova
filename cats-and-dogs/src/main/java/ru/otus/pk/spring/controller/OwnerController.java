package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Owner;
import ru.otus.pk.spring.service.OwnerService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class OwnerController {

    private final OwnerService service;

    @GetMapping("/owners")
    public List<Owner> findAll() {
        return service.findAll();
    }
}
