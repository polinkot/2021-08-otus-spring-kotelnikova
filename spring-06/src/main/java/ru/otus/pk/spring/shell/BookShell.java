package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.service.BookService;

import java.util.List;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService service;

    @ShellMethod(value = "Get Books count", key = {"bcnt", "book-count"})
    public long count() {
        return service.count();
    }

    @ShellMethod(value = "Find all Books", key = {"ball", "book-all"})
    public List<Book> findAll() {
        return service.findAll();
    }

    @ShellMethod(value = "Find Book by id", key = {"bid", "book-id"})
    public Book findById(@ShellOption Long id) {
        return service.findById(id);
    }

    @ShellMethod(value = "Save Book", key = {"bsv", "book-save"})
    public String save(@ShellOption Long id, @ShellOption String name,
                       @ShellOption Long authorId, @ShellOption Long genreId) {
        Book book = service.save(id, name, authorId, genreId);
        return format("Запись успешно сохранена. %s", book);
    }

    @ShellMethod(value = "Delete Book by id", key = {"bdel", "book-delete"})
    public String deleteById(@ShellOption Long id) {
        int result = service.deleteById(id);

        return result == 1 ? "Запись успешно удалена" : "Не удалось удалить запись";
    }
}