package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

//import ru.otus.pk.spring.config.UserLocale;
//import ru.otus.pk.spring.service.LocalizedIOService;
//
//import static ru.otus.pk.spring.config.MessageSourceConfig.*;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {

    //    private final UserLocale userLocale;
//    private final LocalizedIOService localizedIOService;
    private final GenreService service;

    private String userName;

    @ShellMethod(value = "Get Genres count", key = {"gcnt", "genre-count"})
    public int count() {
        return service.count();
    }

    @ShellMethod(value = "Get all Genres", key = {"gall", "genre-all"})
    public List<Genre> getAll() {
        return service.getAll();
    }

    @ShellMethod(value = "Get Genre by id", key = {"gid", "genre-id"})
    public Genre getById(@ShellOption Long id) {
        return service.getById(id);
    }

    @ShellMethod(value = "Insert Genre", key = {"gins", "genre-insert"})
    public String insert(@ShellOption String name) {
        Number result = service.insert(name);

        return result.intValue() > 0 ? "Запись успешно добавлена" : "Не удалось добавить запись";
    }

    @ShellMethod(value = "Update Genre", key = {"gupd", "genre-update"})
    public String update(@ShellOption Long id, @ShellOption String name) {
        int result = service.update(id, name);

        return result == 1 ? "Запись успешно изменена" : "Не удалось изменить запись";
    }

    @ShellMethod(value = "Delete Genre by id", key = {"gdel", "genre-delete"})
    public String deleteById(@ShellOption Long id) {
        int result = service.deleteById(id);

        return result == 1 ? "Запись успешно удалена" : "Не удалось удалить запись";
    }
//    @ShellMethod(value = "Login command", key = {"l", "login"})
//    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
//        this.userName = userName;
//        return localizedIOService.getMessage(SHELL_WELCOME, userName);
//    }
//
//    @ShellMethod(value = "Start quiz", key = {"s", "start"})
//    @ShellMethodAvailability(value = "isQuizAvailable")
//    public String startQuiz() {
//        quizService.startQuiz();
//        return localizedIOService.getMessage(SHELL_QUIZ_COMPLETE);
//    }
//
//    @ShellMethod(value = "Set Locale", key = {"sl", "set-locale"})
//    @ShellMethodAvailability(value = "isQuizAvailable")
//    public String setLocale(@ShellOption(defaultValue = DEFAULT_LANG) String languageTag) {
//        Locale locale = Locale.forLanguageTag(languageTag);
//        userLocale.setLocale(locale);
//        return localizedIOService.getMessage(SHELL_LOCALE_CHANGED, locale.getDisplayLanguage());
//    }
//
//    private Availability isQuizAvailable() {
//        return userName == null ? unavailable(localizedIOService.getMessage(SHELL_FIRST_LOGIN)) : available();
//    }
}