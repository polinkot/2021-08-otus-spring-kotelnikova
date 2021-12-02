package ru.otus.pk.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final BookService bookService;
    private final CommentService service;

    @PostMapping("/comments/add")
    public String save(Comment comment) {
        Long bookId = comment.getBook().getId();
        comment.setBook(bookService.findById(bookId));
        service.save(comment);
        return "redirect:/books/edit?id=" + bookId;
    }

    @RequestMapping("/comments/delete")
    public String delete(@RequestParam("id") Long id, @RequestParam("bookId") Long bookId) {
        service.deleteById(id);
        return "redirect:/books/edit?id=" + bookId;
    }
}
