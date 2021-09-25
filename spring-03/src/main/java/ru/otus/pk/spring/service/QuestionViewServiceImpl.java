package ru.otus.pk.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.Question;

import static java.lang.String.format;

@Service
public class QuestionViewServiceImpl implements QuestionViewService {

    public String asString(Question question) {
        StringBuilder result = new StringBuilder(format("\n%s", question.getValue()));
        question.getAnswers().forEach(answer ->
                result.append(format("\n\t%d %s", answer.getNumber(), answer.getValue())));

        return result.toString();
    }
}