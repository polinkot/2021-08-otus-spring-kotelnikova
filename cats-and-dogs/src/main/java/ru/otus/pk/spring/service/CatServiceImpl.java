package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Cat;
import ru.otus.pk.spring.repository.CatRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatServiceImpl implements CatService {

    public static final String CAT_NOT_FOUND = "Cat not found!!! id = %s";

    private final CatRepository repository;

//    @Transactional(readOnly = true)
//    @Override
//    public Long count() {
//        return repository.count();
//    }

    @Transactional(readOnly = true)
    @Override
    public List<Cat> findAll() {
        return repository.findAll();
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Cat findById(Long id) {
//        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(CAT_NOT_FOUND, id)));
//    }
//
//    @Transactional
//    @Override
//    public Cat save(Cat cat) {
//        validate(cat);
//        return repository.save(cat);
//    }
//
//    @Transactional
//    @Override
//    public void deleteById(Long id) {
//        try {
//            repository.deleteById(id);
//        } catch (EmptyResultDataAccessException e) {
//            throw new ObjectNotFoundException(format(CAT_NOT_FOUND, id), e);
//        }
//    }
//
//    private void validate(Cat cat) {
//        List<String> errors = new ArrayList<>();
//
//        if (isEmpty(cat.getName())) {
//            errors.add("Cat name is null or empty!");
//        }
//
//        if (!isEmpty(errors)) {
//            throw new AppException(join("\n", errors));
//        }
//    }
}
