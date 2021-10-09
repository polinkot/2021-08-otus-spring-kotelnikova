package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.config.UserLocale;
import ru.otus.pk.spring.service.QuizService;

import java.util.Locale;

import static java.lang.String.format;
import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;
import static ru.otus.pk.spring.config.MessageSourceConfig.DEFAULT_LANG;

@ShellComponent
@RequiredArgsConstructor
public class QuizShell {

    private final QuizService quizService;
    private final UserLocale userLocale;

    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return format("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Start quiz", key = {"s", "start"})
    @ShellMethodAvailability(value = "isQuizAvailable")
    public String startQuiz() {
        quizService.startQuiz();
        return "Опрос завершён.";
    }

    @ShellMethod(value = "Set Locale", key = {"sl", "set-locale"})
    @ShellMethodAvailability(value = "isQuizAvailable")
    public String setLocale(@ShellOption(defaultValue = DEFAULT_LANG) String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        userLocale.setLocale(locale);
        return format("Локаль изменена на %s.", locale.getDisplayLanguage());
    }

    private Availability isQuizAvailable() {
        return userName == null ? unavailable("Сначала залогиньтесь") : available();
    }
}