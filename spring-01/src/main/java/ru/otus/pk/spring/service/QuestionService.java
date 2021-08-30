package ru.otus.pk.spring.service;

import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.domain.Quiz;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface QuestionService {

    List<Question> findAll() throws IOException, URISyntaxException;

    Quiz findQuiz() throws IOException, URISyntaxException;
}
