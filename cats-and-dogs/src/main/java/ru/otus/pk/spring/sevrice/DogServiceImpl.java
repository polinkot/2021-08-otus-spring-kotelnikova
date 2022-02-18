package ru.otus.pk.spring.sevrice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Dog;
import ru.otus.pk.spring.repository.DogRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DogServiceImpl implements DogService {

    private final DogRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Dog> findAll() {
        return repository.findAll();
    }
}
