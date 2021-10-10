package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.config.UserLocale;
import ru.otus.pk.spring.service.LocalizedIOService;
import ru.otus.pk.spring.service.QuizService;

import java.util.Locale;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;
import static ru.otus.pk.spring.config.MessageSourceConfig.*;

@ShellComponent
@RequiredArgsConstructor
public class QuizShell {

    private final QuizService quizService;
    private final UserLocale userLocale;
    private final LocalizedIOService localizedIOService;

    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return localizedIOService.getMessage(SHELL_WELCOME, userName);
    }

    @ShellMethod(value = "Start quiz", key = {"s", "start"})
    @ShellMethodAvailability(value = "isQuizAvailable")
    public String startQuiz() {
        quizService.startQuiz();
        return localizedIOService.getMessage(SHELL_QUIZ_COMPLETE);
    }

    @ShellMethod(value = "Set Locale", key = {"sl", "set-locale"})
    @ShellMethodAvailability(value = "isQuizAvailable")
    public String setLocale(@ShellOption(defaultValue = DEFAULT_LANG) String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        userLocale.setLocale(locale);
        return localizedIOService.getMessage(SHELL_LOCALE_CHANGED, locale.getDisplayLanguage());
    }

    private Availability isQuizAvailable() {
        return userName == null ? unavailable(localizedIOService.getMessage(SHELL_FIRST_LOGIN)) : available();
    }
}