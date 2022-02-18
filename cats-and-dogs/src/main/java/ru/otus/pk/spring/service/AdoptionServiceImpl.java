package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Adoption;
import ru.otus.pk.spring.repository.AdoptionRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Adoption> findAll() {
        return repository.findAll();
    }
}
