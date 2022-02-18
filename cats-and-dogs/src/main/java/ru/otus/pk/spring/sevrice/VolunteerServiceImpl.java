package ru.otus.pk.spring.sevrice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Volunteer;
import ru.otus.pk.spring.repository.VolunteerRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Volunteer> findAll() {
        return repository.findAll();
    }
}
