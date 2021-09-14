package ru.otus.pk.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.dao.QuestionDao;
import ru.otus.pk.spring.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    private final InOutService inOutService;

    public QuestionServiceImpl(QuestionDao dao, InOutService inOutService) {
        this.dao = dao;
        this.inOutService = inOutService;
    }

    public List<Question> findAll() {
        return dao.findAll();
    }

    public String asString(List<Question> questions) {
        StringBuilder result = new StringBuilder();

        questions.forEach(question -> {
            result.append(String.format("\n%s", question.getValue()));
            question.getAnswers().forEach(answer -> result.append(String.format("\n\t%s", answer.getValue())));
        });

        return result.toString();
    }

    public void printQuestions() {
        String questions = asString(findAll());
        inOutService.getOut().println(questions);
    }
}