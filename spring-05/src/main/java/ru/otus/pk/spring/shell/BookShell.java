package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.service.BookService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    public static final String WHOLE = "whole";

    private final BookService service;

    @ShellMethod(value = "Get Books count", key = {"bcnt", "book-count"})
    public int count() {
        return service.count();
    }

    @ShellMethod(value = "Get all Books", key = {"ball", "book-all"})
    public List<Book> getAll(@ShellOption(defaultValue = "") String whole) {
        List<Book> books = service.getAll();
        if (!whole.equalsIgnoreCase(WHOLE)) {
            return books;
        }

        return service.getWholeBooks(books);
    }

    @ShellMethod(value = "Get Book by id", key = {"bid", "book-id"})
    public Book getById(@ShellOption Long id, @ShellOption(defaultValue = "") String whole) {
        Book book = service.getById(id);
        if (!whole.equalsIgnoreCase(WHOLE)) {
            return book;
        }

        return service.getWholeBooks(List.of(book)).get(0);
    }

    @ShellMethod(value = "Insert Book", key = {"bins", "book-insert"})
    public String insert(@ShellOption String name, @ShellOption Long authorId, @ShellOption Long genreId) {
        Long id = service.insert(name, authorId, genreId);

        return id > 0 ? "Запись успешно добавлена" : "Не удалось добавить запись";
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
}