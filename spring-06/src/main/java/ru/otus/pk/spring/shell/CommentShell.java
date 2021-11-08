package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.model.Comment;
import ru.otus.pk.spring.service.CommentService;

import java.util.List;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class CommentShell {

    private final CommentService service;

    @ShellMethod(value = "Get Comments count", key = {"ccnt", "comment-count"})
    public Long count() {
        return service.count();
    }

    @ShellMethod(value = "Find all Comments", key = {"call", "comment-all"})
    public List<Comment> findAll() {
        return service.findAll();
    }

    @ShellMethod(value = "Find Comment by id", key = {"cid", "comment-id"})
    public Comment findById(@ShellOption Long id) {
        checkId(id);

        return service.findById(id);
    }

    @ShellMethod(value = "Add Comment", key = {"cadd", "comment-add"})
    public String add(@ShellOption String text, @ShellOption Long bookId) {
        Comment comment = service.save(null, text, bookId);
        return format("Comment has been added successfully.\n%s", comment);
    }

    @ShellMethod(value = "Edit Comment", key = {"cedit", "comment-edit"})
    public String edit(@ShellOption Long id, @ShellOption String text) {
        checkId(id);

        Comment genre = service.save(id, text, null);
        return format("Comment has been updated successfully.\n%s", genre);
    }

    @ShellMethod(value = "Delete Comment by id", key = {"cdel", "comment-delete"})
    public String deleteById(@ShellOption Long id) {
        checkId(id);

        int result = service.deleteById(id);
        return result == 1 ? "Comment has been removed." : "Failed to remove Comment";
    }

    @ShellMethod(value = "Find Comments by bookId", key = {"cbk", "comment-by-book"})
    public List<Comment> findByBookId(@ShellOption Long bookId) {
        CheckUtils.checkId(bookId, "Book id is null!!!");

        return service.findByBookId(bookId);
    }

    private void checkId(Long id) {
        CheckUtils.checkId(id, "Comment id is null!!!");
    }
}