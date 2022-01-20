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
import ru.otus.pk.spring.service.CommentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с комментариями должен ")
@ExtendWith(SpringExtension.class)
@WithMockUser
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final Comment COMMENT = new Comment(1L, "Comment1", BOOK);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private CommentService service;

    @DisplayName("для авторизованного сохранять комментарий")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void saveForUser() throws Exception {
        given(service.save(any(Comment.class))).willReturn(COMMENT);

        this.mockMvc.perform(post("/comments/add")
                .with(csrf())
                .param("text", "comment1")
                .param("book.id", "1"))
                .andExpect(status().isFound());
    }

    @DisplayName("для неавторизованного не сохранять комментарий")
    @Test
    void saveNotAuth() throws Exception {
        given(service.save(any(Comment.class))).willReturn(COMMENT);

        this.mockMvc.perform(post("/comments/add")
                .param("text", "comment1")
                .param("book.id", "1"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("для авторизованного удалять комментарий")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void delete() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/comments/delete")
                .with(csrf())
                .param("id", "1")
                .param("bookId", "1"))
                .andExpect(status().isFound());
    }

    @DisplayName("для неавторизованного не удалять комментарий")
    @Test
    void deleteNotAuth() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(post("/comments/delete")
                .param("id", "1")
                .param("bookId", "1"))
                .andExpect(status().isForbidden());
    }
}