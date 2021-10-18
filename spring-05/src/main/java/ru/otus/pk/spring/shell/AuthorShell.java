package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.service.AuthorService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

    private final AuthorService service;

    @ShellMethod(value = "Get Authors count", key = {"acnt", "author-count"})
    public int count() {
        return service.count();
    }

    @ShellMethod(value = "Get all Authors", key = {"aall", "author-all"})
    public List<Author> getAll() {
        return service.getAll();
    }

    @ShellMethod(value = "Get Author by id", key = {"aid", "author-id"})
    public Author getById(@ShellOption Long id) {
        return service.getById(id);
    }

    @ShellMethod(value = "Insert Author", key = {"ains", "author-insert"})
    public String insert(@ShellOption String firstName, @ShellOption String lastName) {
        Long id = service.insert(firstName, lastName);

        return id > 0 ? "Запись успешно добавлена" : "Не удалось добавить запись";
    }

    @ShellMethod(value = "Update Author", key = {"aupd", "author-update"})
    public String update(@ShellOption Long id, @ShellOption String firstName, @ShellOption String lastName) {
        int result = service.update(id, firstName, lastName);

        return result == 1 ? "Запись успешно изменена" : "Не удалось изменить запись";
    }

    @ShellMethod(value = "Delete Author by id", key = {"adel", "author-delete"})
    public String deleteById(@ShellOption Long id) {
        int result = service.deleteById(id);

        return result == 1 ? "Запись успешно удалена" : "Не удалось удалить запись";
    }
}