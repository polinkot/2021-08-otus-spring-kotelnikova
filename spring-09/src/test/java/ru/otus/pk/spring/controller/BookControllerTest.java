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
import ru.otus.pk.spring.service.AuthorService;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.CommentService;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final Comment COMMENT = new Comment(1L, "Comment1", BOOK);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @Test
    public void finAll() throws Exception {
        given(service.findAll()).willReturn(List.of(BOOK));

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        given(service.findById(anyLong())).willReturn(BOOK);
        given(authorService.findAll()).willReturn(List.of(AUTHOR));
        given(genreService.findAll()).willReturn(List.of(GENRE));
        given(commentService.findByBookId(anyLong())).willReturn(List.of(COMMENT));

        this.mockMvc.perform(get("/books/edit?id=" + BOOK.getId())).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        given(service.save(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(post("/books/edit")
                .param("id", "1")
                .param("name", "book1"))
                .andExpect(status().isFound());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/books/delete")
                .param("id", "1"))
                .andExpect(status().isFound());
    }
}