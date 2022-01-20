package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.*;
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

@DisplayName("Контроллер для работы с книгами должен ")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final Comment COMMENT = new Comment(1L, "Comment1", BOOK);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BookService service;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @DisplayName("для всех возвращать список книг")
    @Test
    public void finAll() throws Exception {
        given(service.findAll()).willReturn(List.of(BOOK));

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @DisplayName("для всех возвращать информацию о книге")
    @Test
    void edit() throws Exception {
        given(service.findById(anyLong())).willReturn(BOOK);
        given(authorService.findAll()).willReturn(List.of(AUTHOR));
        given(genreService.findAll()).willReturn(List.of(GENRE));
        given(commentService.findByBookId(anyLong())).willReturn(List.of(COMMENT));

        this.mockMvc.perform(get("/books/edit?id=" + BOOK.getId())).andDo(print()).andExpect(status().isOk());
    }

    @DisplayName("для роли ADMIN сохранять книгу")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void saveForAdmin() throws Exception {
        given(service.save(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(post("/books/save")
                .with(csrf())
                .param("id", "1")
                .param("name", "book1"))
                .andExpect(status().isFound());
    }

    @DisplayName("для роли USER не сохранять книгу")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void saveForUser() throws Exception {
        given(service.save(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(post("/books/save")
                .param("id", "1")
                .param("name", "book1"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("для неавторизованного не сохранять книгу")
    @Test
    void saveNotAuth() throws Exception {
        given(service.save(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(post("/books/save")
                .param("id", "1")
                .param("name", "book1"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("для роли ADMIN удалять книгу")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void deleteForAdmin() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/books/delete")
                .with(csrf())
                .param("id", "1"))
                .andExpect(status().isFound());
    }

    @DisplayName("для роли USER не удалять книгу")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void deleteForUser() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/books/delete")
                .param("id", "1"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("для неавторизованного не удалять книгу")
    @Test
    void deleteNotAuth() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/books/delete")
                .param("id", "1"))
                .andExpect(status().isForbidden());
    }
}