package ru.otus.pk.spring.dao;

import ru.otus.pk.spring.domain.Question;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface QuestionDao {

    List<Question> findAll() throws IOException, URISyntaxException;
}
