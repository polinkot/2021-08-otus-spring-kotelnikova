package ru.otus.pk.spring.dao;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ru.otus.pk.spring.domain.Answer;
import ru.otus.pk.spring.domain.Question;

import java.io.*;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.stream.Collectors.toList;

public class QuestionDaoSimple implements QuestionDao {
    private final String source;

    public QuestionDaoSimple(String source) {
        if (isNullOrEmpty(source)) {
            throw new IllegalStateException("Empty source CSV path!!!");
        }

        this.source = source;
    }

    public List<Question> findAll() throws IOException {
        try (InputStream in = getClass().getResourceAsStream(source);
             Reader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            List<CsvQuestion> csvQuestions = new CsvToBeanBuilder<CsvQuestion>(reader)
                    .withType(CsvQuestion.class)
                    .build()
                    .parse();

            return csvQuestions.stream().map(CsvQuestion::toQuestion).collect(toList());
        }
    }

    //Вопрос - как лучше делать?
    //Это вспомогательный ДТО для чтения записей из CSV, он нужен только здесь.
    // Оставить его здесь или вынести в отдельный класс?

    //Второй вопрос - стоит ли вообще заводить этот класс?
    //Доменный класс Question такой же, только без CSV аннотаций.
    //Подумала, что в доменном классе лучше CSV аннотации не указывать.
    //Поэтому пришлось создать вот этот CsvQuestion - копия доменного Question, только с CSV аннотациями.
    //Как лучше поступить?
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