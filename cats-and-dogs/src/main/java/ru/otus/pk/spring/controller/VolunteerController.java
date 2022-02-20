package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Volunteer;
import ru.otus.pk.spring.service.VolunteerService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class VolunteerController {

    private final VolunteerService service;

    @GetMapping("/volunteers")
    public List<Volunteer> findAll() {
        return service.findAll();
    }
}
