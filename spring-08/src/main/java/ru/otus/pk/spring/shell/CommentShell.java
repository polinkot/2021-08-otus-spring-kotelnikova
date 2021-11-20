package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.dto.CommentDto;
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

    @ShellMethod(value = "Get all Comments", key = {"call", "comment-all"})
    public List<CommentDto> getAll() {
        return service.getAll();
    }

    @ShellMethod(value = "Get Comment by id", key = {"cid", "comment-id"})
    public CommentDto getById(@ShellOption String id) {
        checkId(id);
        return service.getById(id);
    }

    @ShellMethod(value = "Add Comment", key = {"cadd", "comment-add"})
    public String add(@ShellOption String text, @ShellOption String bookId) {
        CommentDto comment = service.add(text, bookId);
        return format("Comment has been added successfully.\n%s", comment);
    }

    @ShellMethod(value = "Edit Comment", key = {"cedit", "comment-edit"})
    public String edit(@ShellOption String id, @ShellOption String text) {
        checkId(id);

        CommentDto comment = service.edit(id, text);
        return format("Comment has been updated successfully.\n%s", comment);
    }

    @ShellMethod(value = "Delete Comment by id", key = {"cdel", "comment-delete"})
    public String deleteById(@ShellOption String id) {
        checkId(id);

        service.deleteById(id);
        return "Comment has been removed.";
    }

    @ShellMethod(value = "Get Comments by bookId", key = {"cbk", "comment-by-book"})
    public List<CommentDto> findByBookId(@ShellOption String bookId) {
        CheckUtils.checkId(bookId, "Book id is null!!!");
        return service.findByBookId(bookId);
    }

    private void checkId(String id) {
        CheckUtils.checkId(id, "Comment id is null!!!");
    }
}