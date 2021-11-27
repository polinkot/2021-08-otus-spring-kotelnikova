package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {

    private final GenreService service;

    @ShellMethod(value = "Get Genres count", key = {"gcnt", "genre-count"})
    public Integer count() {
        return service.count();
    }

    @ShellMethod(value = "Find all Genres", key = {"gall", "genre-all"})
    public List<Genre> findAll() {
        return service.findAll();
    }

    @ShellMethod(value = "Find Genre by id", key = {"gid", "genre-id"})
    public Genre getById(@ShellOption String id) {
        checkId(id);

        return service.findById(id);
    }

    @ShellMethod(value = "Edit Genre", key = {"gedit", "genre-edit"})
    public String edit(@ShellOption String id, @ShellOption String name) {
        checkId(id);

        service.save(id, name);
        return "Genre has been updated successfully.";
    }

    @ShellMethod(value = "Delete Genre by id", key = {"gdel", "genre-delete"})
    public String deleteById(@ShellOption String id) {
        checkId(id);

        service.deleteById(id);
        return "Genre has been removed.";
    }

    private void checkId(String id) {
        CheckUtils.checkId(id, "Genre id is null!!!");
    }
}