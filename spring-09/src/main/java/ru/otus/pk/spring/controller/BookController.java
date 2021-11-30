package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService service;

    @GetMapping("/")
    public String finAll(Model model) {
        List<Book> books = service.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Book book = service.findById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @PostMapping("/edit")
    public String save(Book book) {
        service.save(book);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(Long id) {
        service.deleteById(id);
        return "redirect:/";
    }
}
