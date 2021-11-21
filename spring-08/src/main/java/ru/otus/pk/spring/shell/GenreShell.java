package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.GenreDto;
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

    @ShellMethod(value = "Get all Genres", key = {"gall", "genre-all"})
    public List<GenreDto> getAll() {
        return service.getAll();
    }

    @ShellMethod(value = "Get Genre by id", key = {"gid", "genre-id"})
    public GenreDto getById(@ShellOption String id) {
        checkId(id);

        return service.getById(id);
    }

    @ShellMethod(value = "Add Genre", key = {"gadd", "genre-add"})
    public String add(@ShellOption String name) {
        Genre genre = service.save(null, name);
        return format("Genre has been added successfully.\n%s", genre);
    }

    @ShellMethod(value = "Edit Genre", key = {"gedit", "genre-edit"})
    public String edit(@ShellOption String id, @ShellOption String name) {
        checkId(id);

        Genre genre = service.save(id, name);
        return format("Genre has been updated successfully.\n%s", genre);
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