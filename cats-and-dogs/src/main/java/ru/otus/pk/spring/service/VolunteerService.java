package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Volunteer;

import java.util.List;

public interface VolunteerService {

    List<Volunteer> findAll();
}
