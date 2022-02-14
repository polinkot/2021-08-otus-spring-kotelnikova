package ru.otus.pk.spring.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.controller.dto.BookDto;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.resilience.ResilienceService;
import ru.otus.pk.spring.service.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static ru.otus.pk.spring.resilience.ResilienceService.EXCEPTIONS;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BookController {

    private final BookService service;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CircuitBreaker bookCircuitBreaker;
    private final ResilienceService resilienceService;

    @GetMapping("/books")
    public List<Book> findAll() {
        return Decorators.ofSupplier(service::findAll)
                .withCircuitBreaker(bookCircuitBreaker)
                .withFallback(EXCEPTIONS, resilienceService::booksFallback)
                .decorate().get();
    }

    @GetMapping("/books/{id}")
    public Book findById(@PathVariable("id") Long id) {
        return Decorators.ofSupplier(() -> service.findById(id))
                .withCircuitBreaker(bookCircuitBreaker)
                .withFallback(EXCEPTIONS, resilienceService::bookFallback)
                .decorate().get();
    }

    @ResponseStatus(CREATED)
    @PostMapping("/books")
    public Book create(@RequestBody BookDto dto) {
        Author author = authorService.findById(dto.getAuthorId());
        Genre genre = genreService.findById(dto.getGenreId());
        Book book = new Book(null, dto.getName(), author, genre);
        return service.save(book);
    }

    @PutMapping(value = "/books")
    public Book update(@RequestBody BookDto dto) {
        Book book = service.findById(dto.getId());
        book.setName(dto.getName());
        return service.save(book);
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
