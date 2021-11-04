package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Comment;
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

    @ShellMethod(value = "Find all Books", key = {"ball", "book-all"})
    public List<Book> findAll() {
        return service.findAll();
    }

    @ShellMethod(value = "Find Book by id", key = {"bid", "book-id"})
    public Book findById(@ShellOption Long id) {
        checkId(id);

        return service.findById(id);
    }

    @ShellMethod(value = "Add Book", key = {"badd", "book-add"})
    public String add(@ShellOption String name,
                      @ShellOption Long authorId, @ShellOption String authorFirstName, @ShellOption String authorLastName,
                      @ShellOption Long genreId, @ShellOption String genreName) {
        Book book = service.save(null, name, authorId, authorFirstName, authorLastName, genreId, genreName);
        return format("Book has been added successfully.\n%s", book);
    }

    @ShellMethod(value = "Edit Book", key = {"bedit", "book-edit"})
    public String edit(@ShellOption Long id, @ShellOption String name) {
        checkId(id);

        Book book = service.save(id, name, null, null, null, null, null);
        return format("Book has been updated successfully.\n%s", book);
    }

    @ShellMethod(value = "Delete Book by id", key = {"bdel", "book-delete"})
    public String deleteById(@ShellOption Long id) {
        checkId(id);

        int result = service.deleteById(id);
        return result == 1 ? "Book has been removed." : "Failed to remove book.";
    }

    @ShellMethod(value = "Find Comments", key = {"bcm", "book-comments"})
    public List<Comment> findComments(@ShellOption Long id) {
        checkId(id);

        return service.findComments(id);
    }

    private void checkId(Long id) {
        CheckUtils.checkId(id, "Book id is null!!!");
    }
}