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
        return format("Book has been saved successfully.\n%s", book);
    }

    @ShellMethod(value = "Add Comment", key = {"bac", "book-add-comment"})
    public String addComment(@ShellOption Long id, @ShellOption String comment) {
        Book book = service.addComment(id, comment);
        return format("Comment has been added successfully.\n%s", book);
    }

    @ShellMethod(value = "Remove Comment", key = {"brc", "book-remove-comment"})
    public String removeComment(@ShellOption Long id, @ShellOption Long commentId) {
        Book book = service.removeComment(id, commentId);
        return format("Comment has been removed successfully.\n%s", book);
    }

    @ShellMethod(value = "Delete Book by id", key = {"bdel", "book-delete"})
    public String deleteById(@ShellOption Long id) {
        int result = service.deleteById(id);
        return result == 1 ? "Book has been removed." : "Failed to remove book.";
    }
}