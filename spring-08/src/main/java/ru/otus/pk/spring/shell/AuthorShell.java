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
    public Integer count() {
        return service.count();
    }

    @ShellMethod(value = "Find all Authors", key = {"aall", "author-all"})
    public List<Author> findAll() {
        return service.findAll();
    }

    @ShellMethod(value = "Find Author by id", key = {"aid", "author-id"})
    public Author findById(@ShellOption String id) {
        checkId(id);
        return service.findById(id);
    }

    @ShellMethod(value = "Edit Author", key = {"aedit", "author-edit"})
    public String edit(@ShellOption String id, @ShellOption String firstName, @ShellOption String lastName) {
        checkId(id);

        service.save(id, firstName, lastName);
        return "Author has been updated successfully.";
    }

    @ShellMethod(value = "Delete Author by id", key = {"adel", "author-delete"})
    public String deleteById(@ShellOption String id) {
        checkId(id);

        service.deleteById(id);
        return "Author has been removed.";
    }

    private void checkId(String id) {
        CheckUtils.checkId(id, "Author id is null!!!");
    }
}