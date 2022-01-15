package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.security.CustomUserDetailsService;
import ru.otus.pk.spring.service.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WithMockUser
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final Comment COMMENT = new Comment(1L, "Comment1", BOOK);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService userDetailsService;

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
                .with(csrf())
                .param("id", "1")
                .param("name", "book1"))
                .andExpect(status().isFound());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/books/delete")
                .with(csrf())
                .param("id", "1"))
                .andExpect(status().isFound());
    }
}