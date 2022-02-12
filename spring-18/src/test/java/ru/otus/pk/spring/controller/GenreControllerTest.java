package ru.otus.pk.spring.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.service.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с жанрами должен ")
@WebMvcTest(controllers = GenreController.class)
public class GenreControllerTest {

    private static final Genre GENRE = new Genre(1L, "Genre1");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService service;

    @MockBean
    private CircuitBreaker circuitBreaker;

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    public void shouldReturnExpectedGenresList() throws Exception {
        given(service.findAll()).willReturn(List.of(GENRE));

        this.mockMvc.perform(get("/api/v1/genres")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(GENRE.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(GENRE.getName())));
    }
}