package ru.otus.pk.spring.service;

import ru.otus.pk.spring.dao.QuestionDao;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.Quiz;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public List<Question> findAll() throws IOException, URISyntaxException {
        return dao.findAll();
    }

    public Quiz findQuiz() throws IOException, URISyntaxException {
        return new Quiz(findAll());
    }
}
