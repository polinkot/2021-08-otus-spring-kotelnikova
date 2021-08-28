package ru.otus.pk.spring.service;

import com.opencsv.exceptions.CsvException;
import ru.otus.pk.spring.dao.QuestionDao;
import ru.otus.pk.spring.domain.Question;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public List<Question> findAll() throws IOException, CsvException, URISyntaxException {
        return dao.findAll();
    }
}
