package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;
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

    @ShellMethod(value = "Find all Authors", key = {"aall", "author-all"})
    public List<Author> findAll() {
        return service.findAll();
    }

    @ShellMethod(value = "Find Author by id", key = {"aid", "author-id"})
    public Author findById(@ShellOption Long id) {
        return service.findById(id);
    }

    @ShellMethod(value = "Add Author", key = {"aadd", "author-add"})
    public String add(@ShellOption String firstName, @ShellOption String lastName) {
        Author author = service.save(null, firstName, lastName);
        return format("Author has been added successfully.\n%s", author);
    }

    @ShellMethod(value = "Edit Author", key = {"aedit", "author-edit"})
    public String edit(@ShellOption Long id, @ShellOption String firstName, @ShellOption String lastName) {
        if (id == null) {
            throw new LibraryException("Author id == null!!!");
        }

        Author author = service.save(id, firstName, lastName);
        return format("Author has been updated successfully.\n%s", author);
    }

    @ShellMethod(value = "Delete Author by id", key = {"adel", "author-delete"})
    public String deleteById(@ShellOption Long id) {
        int result = service.deleteById(id);
        return result == 1 ? "Author has been removed." : "Failed to remove author.";
    }

    @ShellMethod(value = "Find Books", key = {"abk", "author-books"})
    public List<Book> findBooks(@ShellOption Long id) {
        return service.findBooks(id);
    }
}