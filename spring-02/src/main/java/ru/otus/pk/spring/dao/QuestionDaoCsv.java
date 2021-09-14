package ru.otus.pk.spring.dao;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.domain.Answer;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.exception.AppException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class QuestionDaoCsv implements QuestionDao {

    @Value("${questions.source}")
    private String source;

    public List<Question> findAll() {
        try (InputStream in = getClass().getResourceAsStream(source);
             Reader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            List<CsvQuestion> csvQuestions = new CsvToBeanBuilder<CsvQuestion>(reader)
                    .withType(CsvQuestion.class)
                    .build()
                    .parse();

            return csvQuestions.stream().map(CsvQuestion::toQuestion).collect(toList());
        } catch (Exception e) {
            throw new AppException("Failed to fetch questions. ", e);
        }
    }

    @Data
    public static class CsvQuestion {

        @CsvBindByName(column = "question")
        private String question;

        @CsvBindAndSplitByName(elementType = Answer.class, splitOn = "\\|", converter = TextToAnswer.class)
        private List<Answer> answers;

        public static class TextToAnswer extends AbstractCsvConverter {

            @Override
            public Object convertToRead(String value) {
                return new Answer(value);
            }
        }

        public Question toQuestion() {
            return new Question(question, answers);
        }
    }
}