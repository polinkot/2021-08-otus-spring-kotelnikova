package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> findAll();

    Owner findById(Long id);

    Owner save(Owner owner);

    void deleteById(Long id);
}
