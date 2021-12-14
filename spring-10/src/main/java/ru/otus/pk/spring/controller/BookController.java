package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.controller.dto.BookDto;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.AuthorService;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService service;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/books")
    public List<Book> finAll() {
        return service.findAll();
    }

    @GetMapping("/books/{id}")
    public Book findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    //    @GetMapping("/books/edit")
//    public String edit(@RequestParam("id") Long id, Model model) {
//        Book book = id > 0 ? service.findById(id) : new Book();
//        model.addAttribute("book", book);
//
//        List<Author> authors = authorService.findAll();
//        model.addAttribute("authors", authors);
//
//        List<Genre> genres = genreService.findAll();
//        model.addAttribute("genres", genres);
//
//        List<Comment> comments = id > 0 ? commentService.findByBookId(id) : new ArrayList<>();
//        model.addAttribute("comments", comments);
//
//        model.addAttribute("comment", new Comment(null, null, book));
//
//        return "book";
//    }
//

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
