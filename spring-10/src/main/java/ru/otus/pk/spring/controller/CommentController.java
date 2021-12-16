package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.controller.dto.CommentDto;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.CommentService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final BookService bookService;
    private final CommentService service;

    @GetMapping("/book/{bookId}/comments")
    public List<Comment> findByBookId(@PathVariable("bookId") Long bookId) {
        return service.findByBookId(bookId);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/comments")
    public Comment create(@RequestBody CommentDto dto) {
        Book book = bookService.findById(dto.getBookId());
        Comment comment = new Comment(null, dto.getText(), book);
        return service.save(comment);
    }

    @DeleteMapping("/comments/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
