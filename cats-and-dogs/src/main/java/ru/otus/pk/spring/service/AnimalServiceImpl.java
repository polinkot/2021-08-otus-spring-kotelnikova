package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Animal;
import ru.otus.pk.spring.domain.Animal.AnimalStatus;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.AnimalRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {

    private static final String ANIMAL_NOT_FOUND = "Animal not found!!! id = %s";

    private final AnimalRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Animal> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Animal findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(ANIMAL_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Animal save(Animal animal) {
        validate(animal);
        return repository.save(animal);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format(ANIMAL_NOT_FOUND, id), e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Animal> findByStatus(AnimalStatus status) {
        return repository.findByStatus(status);
    }

    private void validate(Animal animal) {
        List<String> errors = new ArrayList<>();

        if (isEmpty(animal.getName())) {
            errors.add("Animal name is null or empty!");
        }

        if (!isEmpty(errors)) {
            throw new AppException(join("\n", errors));
        }
    }
}
