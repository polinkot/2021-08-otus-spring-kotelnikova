package ru.otus.pk.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.dao.QuestionDao;
import ru.otus.pk.spring.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public List<Question> findAll() {
        return dao.findAll();
    }
}