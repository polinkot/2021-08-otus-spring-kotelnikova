package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.dto.AuthorDto;
import ru.otus.pk.spring.service.AuthorService;

import java.util.List;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

    private final AuthorService service;

    @ShellMethod(value = "Get Authors count", key = {"acnt", "author-count"})
    public Long count() {
        return service.count();
    }

    @ShellMethod(value = "Get all Authors", key = {"aall", "author-all"})
    public List<AuthorDto> getAll() {
        return service.getAll();
    }

    @ShellMethod(value = "Get Author by id", key = {"aid", "author-id"})
    public AuthorDto getById(@ShellOption String id) {
        checkId(id);
        return service.getById(id);
    }

    @ShellMethod(value = "Add Author", key = {"aadd", "author-add"})
    public String add(@ShellOption String firstName, @ShellOption String lastName) {
        Author author = service.save(null, firstName, lastName);
        return format("Author has been added successfully.\n%s", author);
    }

    @ShellMethod(value = "Edit Author", key = {"aedit", "author-edit"})
    public String edit(@ShellOption String id, @ShellOption String firstName, @ShellOption String lastName) {
        checkId(id);

        Author author = service.save(id, firstName, lastName);
        return format("Author has been updated successfully.\n%s", author);
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