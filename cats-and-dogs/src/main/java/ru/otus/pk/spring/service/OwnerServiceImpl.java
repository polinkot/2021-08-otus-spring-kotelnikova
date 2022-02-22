package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Owner;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private static final String OWNER_NOT_FOUND = "Owner not found!!! id = %s";

    private final OwnerRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Owner> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Owner findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(OWNER_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Owner save(Owner owner) {
        validate(owner);
        return repository.save(owner);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format(OWNER_NOT_FOUND, id), e);
        }
    }

    private void validate(Owner owner) {
        List<String> errors = new ArrayList<>();

        if (isEmpty(owner.getName())) {
            errors.add("Owner name is null or empty!");
        }

        if (!isEmpty(errors)) {
            throw new AppException(join("\n", errors));
        }
    }
}
