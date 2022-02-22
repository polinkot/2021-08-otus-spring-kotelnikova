package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Adoption;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.AdoptionRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class AdoptionServiceImpl implements AdoptionService {

    private static final String ADOPTION_NOT_FOUND = "Adoption not found!!! id = %s";

    private final AdoptionRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Adoption> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Adoption findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(ADOPTION_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Adoption save(Adoption adoption) {
        validate(adoption);
        return repository.save(adoption);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format(ADOPTION_NOT_FOUND, id), e);
        }
    }

    private void validate(Adoption adoption) {
        List<String> errors = new ArrayList<>();

        if (isEmpty(adoption.getId() != null)) {
            errors.add("Adoption can't be updated!");
        }

        if (isEmpty(adoption.getAnimal())) {
            errors.add("Adoption animal is null or empty!");
        }

        if (isEmpty(adoption.getOwner())) {
            errors.add("Adoption owner is null or empty!");
        }

//        if (isEmpty(adoption.getVolunteer())) {
//            errors.add("Adoption volunteer is null or empty!");
//        }

        if (!isEmpty(errors)) {
            throw new AppException(join("\n", errors));
        }
    }
}
