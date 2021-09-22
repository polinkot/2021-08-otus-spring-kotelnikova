package ru.otus.pk.spring.dto;


import lombok.Data;
import ru.otus.pk.spring.domain.Question;
import ru.otus.pk.spring.exception.AppException;
import ru.otus.pk.spring.service.InOutService;

import java.util.List;

import static java.lang.String.format;

//Вопросы
//Начала исправлять замечание "Данный метод содержит логику нескольких действий и может быть дополнительно разбит на 3+ приватных"
//В результате получилось очень много приватных методов.
//Решила вынести в отдельный класс.
//Вынесла. Но осталось много вопросов.

//1. Quiz - это не доменный класс. У него нет ДАО. Не хранится нигде (у нас же нет требований сохранять результаты, просто отображаем и всё).
//   Это просто временный класс.
//   Значит - ему можно передать InOutService?
//   Ведь этот класс специально предназначен для работы с IO, не предполагается, что он может работать с другими представлениями.
//
//2. Назвала пакет dto. Но наверно это всё-таки не dto. Много логики. Но и не сервис. У него же есть состояние.
//   Просто класс приложения. Куда его положить?
//   Может его лучше сделать внутренним в QuizServiceImpl? Он ведь только для QuizServiceImpl нужен.
//   Снаружи QuizServiceImpl нигде не будет использоваться.
//   С такими классами всё время сомневаюсь - где их делать внутри класса, в котором они используются? Или в отдельном классе?
//   Если класс просто вспомогательный для другого класса - пусть он тогда в этом классе и живёт? Но что с тестированием тогда?

@Data
public
class Quiz {
    private final InOutService ioService;
    private final List<Question> questions;
    private final int correctMin;

    private String firstName;
    private String lastName;
    private int correctAnswers;

    public void increaseCorrectAnswers() {
        correctAnswers++;
    }

    public void requestFirstName() {
        println("Please, input your first name: ");
        this.firstName = nextLine();
    }

    public void requestLastName() {
        println("Please, input your last name: ");
        this.lastName = nextLine();
    }

    public void askQuestions() {
        println("Please, answer the questions: ");
        questions.forEach(question -> {
            println(asString(question));

            int correctAnswer = question.getCorrectAnswer()
                    .orElseThrow(() -> new AppException("No correct answer for question: " + this))
                    .getNumber();
            int answer = readAnswer();
            if (answer == correctAnswer) {
                increaseCorrectAnswers();
            }
        });
    }

    public void printResult() {
        println(format("\nYou have answered correctly %d questions of %d.%n",
                correctAnswers, questions.size()));
        println(correctAnswers >= correctMin ?
                "Congratulations! You have passed the test!" :
                "You haven't passed the test. Try again!");
    }

    private String asString(Question question) {
        StringBuilder result = new StringBuilder(format("\n%s", question.getValue()));
        question.getAnswers().forEach(answer ->
                result.append(format("\n\t%d %s", answer.getNumber(), answer.getValue())));

        return result.toString();
    }

    private int readAnswer() {
        for (int i = 0; i < 3; i++) {
            println("Please enter an integer number: ");
            if (hasNextInt()) {
                return nextInt();
            }

            nextLine();
            println("Incorrect format.");
        }

        throw new AppException("Error!!! Incorrect answer format!!!");
    }

    private void println(String text) {
        ioService.println(text);
    }

    private String nextLine() {
        return ioService.nextLine();
    }

    private boolean hasNextInt() {
        return ioService.hasNextInt();
    }

    private int nextInt() {
        return ioService.nextInt();
    }
}