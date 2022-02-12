package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.controller.dto.CommentDto;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.domain.mapper.CommentMapper;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.CommentService;

import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.pk.spring.controller.Utils.asJsonString;

@DisplayName("Контроллер для работы с комментариями должен ")
@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final Comment COMMENT = new Comment(1L, "Comment1", BOOK);
    private static final CommentDto COMMENT_DTO = new CommentDto(COMMENT.getId(), COMMENT.getText(), BOOK.getId());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService service;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentMapper mapper;

    @DisplayName("возвращать ожидаемый список комментариев для книги ")
    @Test
    void shouldReturnExpectedBookComments() throws Exception {
        given(service.findByBookId(anyLong())).willReturn(List.of(COMMENT));
        given(mapper.toDto(any(Comment.class))).willReturn(COMMENT_DTO);

        this.mockMvc.perform(get(format("/api/v1/book/%s/comments", BOOK.getId()))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(COMMENT_DTO.getId().intValue())))
                .andExpect(jsonPath("$[0].text", is(COMMENT_DTO.getText())))
                .andExpect(jsonPath("$[0].bookId", is(COMMENT_DTO.getBookId().intValue())));
    }

    @DisplayName("добавлять комментарий")
    @Test
    public void shouldAddComment() throws Exception {
        given(service.save(any(Comment.class))).willReturn(COMMENT);

        this.mockMvc.perform(post("/api/v1/comments")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(COMMENT_DTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(COMMENT.getId().intValue())))
                .andExpect(jsonPath("$.text", is(COMMENT.getText())))
                .andExpect(jsonPath("$.book.id", is(BOOK.getId().intValue())));
    }

    @DisplayName("удалять комментарий")
    @Test
    void shouldDeleteComment() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/comments/" + COMMENT.getId()))
                .andExpect(status().isOk());
    }
}