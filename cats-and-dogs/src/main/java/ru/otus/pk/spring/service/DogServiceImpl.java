package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Dog;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.DogRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class DogServiceImpl implements DogService {

    public static final String DOG_NOT_FOUND = "Dog not found!!! id = %s";

    private final DogRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Dog> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Dog findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(DOG_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Dog save(Dog dog) {
        validate(dog);
        return repository.save(dog);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format(DOG_NOT_FOUND, id), e);
        }
    }

    private void validate(Dog dog) {
        List<String> errors = new ArrayList<>();

        if (isEmpty(dog.getName())) {
            errors.add("Dog name is null or empty!");
        }

        if (!isEmpty(errors)) {
            throw new AppException(join("\n", errors));
        }
    }
}
