package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.CommentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final Comment COMMENT = new Comment(1L, "Comment1", BOOK);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService service;

    @Test
    void save() throws Exception {
        given(service.save(any(Comment.class))).willReturn(COMMENT);

        this.mockMvc.perform(post("/comments/add")
                .param("text", "comment1")
                .param("book.id", "1"))
                .andExpect(status().isFound());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/comments/delete")
                .param("id", "1")
                .param("bookId", "1"))
                .andExpect(status().isFound());
    }
}