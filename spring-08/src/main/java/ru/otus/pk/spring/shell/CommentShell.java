package ru.otus.pk.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pk.spring.dto.CommentDto;
import ru.otus.pk.spring.dto.CommentMapper;
import ru.otus.pk.spring.service.CommentService;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@ShellComponent
@RequiredArgsConstructor
public class CommentShell {

    private final CommentService service;
    private final CommentMapper mapper;

    @ShellMethod(value = "Get Comments count", key = {"ccnt", "comment-count"})
    public Long count() {
        return service.count();
    }

    @ShellMethod(value = "Find all Comments", key = {"call", "comment-all"})
    public List<CommentDto> findAll() {
        return service.findAll().stream().map(mapper::toDto).collect(toList());
    }

    @ShellMethod(value = "Find Comment by id", key = {"cid", "comment-id"})
    public CommentDto findById(@ShellOption String id) {
        checkId(id);
        return mapper.toDto(service.findById(id));
    }

    @ShellMethod(value = "Add Comment", key = {"cadd", "comment-add"})
    public String add(@ShellOption String text, @ShellOption String bookId) {
        CommentDto comment = mapper.toDto(service.add(text, bookId));
        return format("Comment has been added successfully.\n%s", comment);
    }

    @ShellMethod(value = "Edit Comment", key = {"cedit", "comment-edit"})
    public String edit(@ShellOption String id, @ShellOption String text) {
        checkId(id);

        CommentDto comment = mapper.toDto(service.edit(id, text));
        return format("Comment has been updated successfully.\n%s", comment);
    }

    @ShellMethod(value = "Delete Comment by id", key = {"cdel", "comment-delete"})
    public String deleteById(@ShellOption String id) {
        checkId(id);

        service.deleteById(id);
        return "Comment has been removed.";
    }

    @ShellMethod(value = "Find Comments by bookId", key = {"cbk", "comment-by-book"})
    public List<CommentDto> findByBookId(@ShellOption String bookId) {
        CheckUtils.checkId(bookId, "Book id is null!!!");
        return service.findByBookId(bookId).stream().map(mapper::toDto).collect(toList());
    }

    private void checkId(String id) {
        CheckUtils.checkId(id, "Comment id is null!!!");
    }
}