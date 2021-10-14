package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.service.BookService;

import java.util.List;

import static java.lang.Boolean.parseBoolean;

//import ru.otus.pk.spring.config.UserLocale;
//import ru.otus.pk.spring.service.LocalizedIOService;
//
//import static ru.otus.pk.spring.config.MessageSourceConfig.*;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    //    private final UserLocale userLocale;
//    private final LocalizedIOService localizedIOService;
    private final BookService service;

    private String userName;

    @ShellMethod(value = "Get Books count", key = {"bcnt", "book-count"})
    public int count() {
        return service.count();
    }

    @ShellMethod(value = "Get all Books", key = {"ball", "book-all"})
    public List<Book> getAll() {
        return service.getAll();
    }

    @ShellMethod(value = "Get Book by id", key = {"bid", "book-id"})
    public Book getById(@ShellOption Long id, @ShellOption(defaultValue = "false") String complete) {
        return parseBoolean(complete) ? service.getByIdComplete(id) : service.getById(id);
    }

    @ShellMethod(value = "Insert Book", key = {"bins", "book-insert"})
    public String insert(@ShellOption String name, @ShellOption Long authorId, @ShellOption Long genreId) {
        Number result = service.insert(name, authorId, genreId);

        return result.intValue() > 0 ? "Запись успешно добавлена" : "Не удалось добавить запись";
    }

    @ShellMethod(value = "Update Book", key = {"bupd", "book-update"})
    public String update(@ShellOption Long id, @ShellOption String name,
                         @ShellOption Long authorId, @ShellOption Long genreId) {
        int result = service.update(id, name, authorId, genreId);

        return result == 1 ? "Запись успешно изменена" : "Не удалось изменить запись";
    }

    @ShellMethod(value = "Delete Book by id", key = {"bdel", "book-delete"})
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