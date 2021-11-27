package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {

    private final GenreService service;

    @ShellMethod(value = "Get Genres count", key = {"gcnt", "genre-count"})
    public Long count() {
        return service.count();
    }

    @ShellMethod(value = "Find all Genres", key = {"gall", "genre-all"})
    public List<Genre> findAll() {
        return service.findAll();
    }

    @ShellMethod(value = "Find Genre by id", key = {"gid", "genre-id"})
    public Genre findById(@ShellOption Long id) {
        checkId(id);

        return service.findById(id);
    }

    @ShellMethod(value = "Add Genre", key = {"gadd", "genre-add"})
    public String add(@ShellOption String name) {
        Genre genre = service.save(null, name);
        return format("Genre has been added successfully.\n%s", genre);
    }

    @ShellMethod(value = "Edit Genre", key = {"gedit", "genre-edit"})
    public String edit(@ShellOption Long id, @ShellOption String name) {
        checkId(id);

        Genre genre = service.save(id, name);
        return format("Genre has been updated successfully.\n%s", genre);
    }

    @ShellMethod(value = "Delete Genre by id", key = {"gdel", "genre-delete"})
    public String deleteById(@ShellOption Long id) {
        checkId(id);

        service.deleteById(id);
        return "Genre has been removed.";
    }

    private void checkId(Long id) {
        CheckUtils.checkId(id, "Genre id is null!!!");
    }
}