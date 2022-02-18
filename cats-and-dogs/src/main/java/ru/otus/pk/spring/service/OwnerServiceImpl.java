package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Owner;
import ru.otus.pk.spring.repository.OwnerRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Owner> findAll() {
        return repository.findAll();
    }
}
