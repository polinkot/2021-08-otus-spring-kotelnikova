package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.controller.dto.BookDto;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.AuthorService;
import ru.otus.pk.spring.service.BookService;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

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

@DisplayName("Контроллер для работы с книгами должен ")
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, "Book1", AUTHOR, GENRE);
    private static final BookDto BOOK_DTO = new BookDto(BOOK.getId(), BOOK.getName(), AUTHOR.getId(), GENRE.getId());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    public void shouldReturnExpectedBooksList() throws Exception {
        given(service.findAll()).willReturn(List.of(BOOK));

        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(BOOK.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(BOOK.getName())))
                .andExpect(jsonPath("$[0].author.id", is(AUTHOR.getId().intValue())))
                .andExpect(jsonPath("$[0].genre.id", is(GENRE.getId().intValue())));
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    public void shouldReturnExpectedBookById() throws Exception {
        given(service.findById(BOOK.getId())).willReturn(BOOK);

        this.mockMvc.perform(get("/books/" + BOOK.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(BOOK.getId().intValue())))
                .andExpect(jsonPath("$.name", is(BOOK.getName())))
                .andExpect(jsonPath("$.author.id", is(AUTHOR.getId().intValue())))
                .andExpect(jsonPath("$.genre.id", is(GENRE.getId().intValue())));
    }

    @DisplayName("добавлять книгу")
    @Test
    public void shouldAddBook() throws Exception {
        given(service.save(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(post("/books")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(BOOK_DTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(BOOK.getId().intValue())))
                .andExpect(jsonPath("$.name", is(BOOK.getName())))
                .andExpect(jsonPath("$.author.id", is(AUTHOR.getId().intValue())))
                .andExpect(jsonPath("$.genre.id", is(GENRE.getId().intValue())));
    }

    @DisplayName("редактировать книгу")
    @Test
    public void shouldUpdateBook() throws Exception {
        given(service.findById(anyLong())).willReturn(BOOK);
        given(service.save(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(put("/books")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(BOOK_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(BOOK.getId().intValue())))
                .andExpect(jsonPath("$.name", is(BOOK.getName())))
                .andExpect(jsonPath("$.author.id", is(AUTHOR.getId().intValue())))
                .andExpect(jsonPath("$.genre.id", is(GENRE.getId().intValue())));
    }

    @DisplayName("удалять книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/books/" + BOOK.getId()))
                .andExpect(status().isOk());
    }
}