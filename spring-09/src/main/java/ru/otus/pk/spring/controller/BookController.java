package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.AuthorService;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.CommentService;
import ru.otus.pk.spring.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService service;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @GetMapping({"/", "/books"})
    public String finAll(Model model) {
        List<Book> books = service.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Book book = id > 0 ? service.findById(id) : new Book();
        model.addAttribute("book", book);

        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);

        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        List<Comment> comments = id > 0 ? commentService.findByBookId(id) : new ArrayList<>();
        model.addAttribute("comments", comments);

        model.addAttribute("comment", new Comment(null, null, book));

        return "book";
    }

    @PostMapping("/books/edit")
    public String save(Book book) {
        service.save(book);
        return "redirect:/books";
    }

    @RequestMapping("/books/delete")
    public String delete(@RequestParam("id") Long id) {
        service.deleteById(id);
        return "redirect:/books";
    }
}
