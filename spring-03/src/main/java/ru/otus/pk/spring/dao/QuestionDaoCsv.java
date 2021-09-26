package ru.otus.pk.spring.dao;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.dao.exception.QuestionsReadingException;
import ru.otus.pk.spring.domain.Answer;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.service.CsvSourceProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Repository
public class QuestionDaoCsv implements QuestionDao {

    private final CsvSourceProvider csvSourceProvider;

    public List<Question> findAll() {
        try (InputStream in = getClass().getResourceAsStream(csvSourceProvider.getCsvSource());
             Reader reader = new BufferedReader(new InputStreamReader(in, UTF_8))
        ) {
            List<CsvQuestion> csvQuestions = new CsvToBeanBuilder<CsvQuestion>(reader)
                    .withType(CsvQuestion.class)
                    .build()
                    .parse();

            return csvQuestions.stream().map(CsvQuestion::toQuestion).collect(toList());
        } catch (Exception e) {
            throw new QuestionsReadingException(e);
        }
    }

    @Data
    public static class CsvQuestion {

        @CsvBindByName(column = "question")
        private String questionValue;

        @CsvBindAndSplitByName(elementType = Answer.class, splitOn = "\\|", converter = TextToAnswer.class)
        private List<Answer> answers;

        public static class TextToAnswer extends AbstractCsvConverter {
            static final String CORRECT_SIGN = "(*)";

            @Override
            public Object convertToRead(String value) {
                return value.contains(CORRECT_SIGN) ?
                        new Answer(value.replace(CORRECT_SIGN, ""), true) :
                        new Answer(value, false);
            }
        }

        public Question toQuestion() {
            Question question = new Question(questionValue);
            question.setAnswers(answers);
            return question;
        }
    }
}