package ru.otus.pk.spring.sevrice;

import ru.otus.pk.spring.domain.Volunteer;

import java.util.List;

public interface VolunteerService {

    List<Volunteer> findAll();
}
