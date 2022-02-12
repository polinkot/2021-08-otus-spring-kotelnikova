package ru.otus.pk.spring.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.controller.dto.CommentDto;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.domain.mapper.CommentMapper;
import ru.otus.pk.spring.resilience.Utils;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.CommentService;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static ru.otus.pk.spring.resilience.Utils.EXCEPTIONS;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CommentController {

    private final BookService bookService;
    private final CommentService service;
    private final CommentMapper mapper;
    private final CircuitBreaker commentCircuitBreaker;

    @GetMapping("/books/{bookId}/comments")
    public List<CommentDto> findByBookId(@PathVariable("bookId") Long bookId) {
        return Decorators.ofSupplier(() -> service.findByBookId(bookId))
                .withCircuitBreaker(commentCircuitBreaker)
                .withFallback(EXCEPTIONS, Utils::commentsFallback)
                .decorate().get().stream().map(mapper::toDto).collect(toList());
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
