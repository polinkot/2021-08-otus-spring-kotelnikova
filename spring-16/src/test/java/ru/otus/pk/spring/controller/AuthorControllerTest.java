package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.service.AuthorService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с авторами должен ")
@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {

    private static final Author AUTHOR = new Author(1L, "AuthorF", "AuthorL");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService service;

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    public void shouldReturnExpectedAuthorsList() throws Exception {
        given(service.findAll()).willReturn(List.of(AUTHOR));

        this.mockMvc.perform(get("/api/v1/authors")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(AUTHOR.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(AUTHOR.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(AUTHOR.getLastName())));
    }
}