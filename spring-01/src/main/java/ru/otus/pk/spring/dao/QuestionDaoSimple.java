package ru.otus.pk.spring.dao;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.pk.spring.domain.Answer;
import ru.otus.pk.spring.domain.Question;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Files.newBufferedReader;
import static org.springframework.util.CollectionUtils.isEmpty;

public class QuestionDaoSimple implements QuestionDao {

    public List<Question> findAll() throws CsvException, IOException, URISyntaxException {
        List<Question> questions = new ArrayList<>();

        List<String[]> lines = readAll("csv/questions.csv");
        lines.forEach(line -> {
            List<Answer> answers = Arrays.stream(line).skip(1)
                    .filter(s -> !s.isEmpty())
                    .map(Answer::new)
                    .collect(Collectors.toList());

            if (!isEmpty(answers)) {
                questions.add(new Question(line[0], answers));
            }
        });

        return questions;
    }

    private static List<String[]> readAll(String path) throws URISyntaxException, IOException, CsvException {
        Reader reader = newBufferedReader(Paths.get(getSystemResource(path).toURI()));

        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();

        return list;
    }
}
