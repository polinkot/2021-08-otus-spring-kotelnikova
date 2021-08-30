package ru.otus.pk.spring.dao;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ru.otus.pk.spring.domain.Answer;
import ru.otus.pk.spring.domain.Question;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Files.newBufferedReader;
import static java.util.stream.Collectors.toList;

public class QuestionDaoSimple implements QuestionDao {
    private final String source;

    public QuestionDaoSimple(String source) {
        if (isNullOrEmpty(source)) {
            throw new IllegalStateException("Empty source CSV path!!!");
        }

        this.source = source;
    }

    public List<Question> findAll() throws IOException, URISyntaxException {
        Reader reader = newBufferedReader(Paths.get(getSystemResource(source).toURI()));

        List<CsvQuestion> csvQuestions = new CsvToBeanBuilder<CsvQuestion>(reader)
                .withType(CsvQuestion.class)
                .build()
                .parse();

        reader.close();

        return csvQuestions.stream().map(CsvQuestion::toQuestion).collect(toList());
    }

    //Вопрос - как лучше делать?
    //Этот класс вспомогательный для чтения записей из CSV, он нужен только здесь.
    // Оставить его здесь или вынести в отдельный класс?
    @Data
    public static class CsvQuestion {

        @CsvBindByName(column = "question")
        private String question;

        @CsvBindAndSplitByName(elementType = Answer.class, splitOn = "\\|", converter = TextToAnswer.class)
        List<Answer> answers;

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