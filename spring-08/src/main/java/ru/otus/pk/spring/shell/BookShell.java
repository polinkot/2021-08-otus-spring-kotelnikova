package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.dto.BookDto;
import ru.otus.pk.spring.service.BookService;

import java.util.List;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService service;

    @ShellMethod(value = "Get Books count", key = {"bcnt", "book-count"})
    public Long count() {
        return service.count();
    }

    @ShellMethod(value = "Get all Books", key = {"ball", "book-all"})
    public List<BookDto> getAll() {
        return service.getAll();
    }

    @ShellMethod(value = "Get Book by id", key = {"bid", "book-id"})
    public BookDto getById(@ShellOption String id) {
        checkId(id);
        return service.getById(id);
    }

    @ShellMethod(value = "Add Book", key = {"badd", "book-add"})
    public String add(@ShellOption String name,
                      @ShellOption String authorId, @ShellOption String authorFirstName, @ShellOption String authorLastName,
                      @ShellOption String genreId, @ShellOption String genreName) {
        BookDto book = service.add(name, authorId, authorFirstName, authorLastName, genreId, genreName);
        return format("Book has been added successfully.\n%s", book);
    }

    @ShellMethod(value = "Edit Book", key = {"bedit", "book-edit"})
    public String edit(@ShellOption String id, @ShellOption String name) {
        checkId(id);

        BookDto book = service.edit(id, name);
        return format("Book has been updated successfully.\n%s", book);
    }

    @ShellMethod(value = "Delete Book by id", key = {"bdel", "book-delete"})
    public String deleteById(@ShellOption String id) {
        checkId(id);

        service.deleteById(id);
        return "Book has been removed.";
    }

    @ShellMethod(value = "Get Books by Author Id", key = {"ba", "author-books"})
    public List<BookDto> getByAuthorId(@ShellOption String authorId) {
        CheckUtils.checkId(authorId, "Author id is null!!!");
        return service.getByAuthorId(authorId);
    }

    @ShellMethod(value = "Get Books by Genre Id", key = {"bg", "genre-books"})
    public List<BookDto> getByGenreId(@ShellOption String genreId) {
        CheckUtils.checkId(genreId, "Genre id is null!!!");
        return service.getByGenreId(genreId);
    }

    private void checkId(String id) {
        CheckUtils.checkId(id, "Book id is null!!!");
    }
}