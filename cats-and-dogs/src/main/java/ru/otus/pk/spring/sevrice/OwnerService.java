package ru.otus.pk.spring.sevrice;

import ru.otus.pk.spring.domain.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> findAll();
}
