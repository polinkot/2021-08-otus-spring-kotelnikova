package ru.otus.pk.spring.service;

import com.opencsv.exceptions.CsvException;
import ru.otus.pk.spring.domain.Question;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface QuestionService {

    List<Question> findAll() throws IOException, CsvException, URISyntaxException;
}
