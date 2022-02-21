package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Cat;
import ru.otus.pk.spring.service.CatService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.pk.spring.domain.Gender.FEMALE;

@DisplayName("Контроллер для работы с кошками должен ")
@WebMvcTest(controllers = CatController.class)
public class CatControllerTest {

    private static final Cat CAT = new Cat(1L, "Cat1", FEMALE, 1, true, true);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService service;

    @DisplayName("возвращать ожидаемый список кошек")
    @Test
    public void shouldReturnExpectedCatsList() throws Exception {
        given(service.findAll()).willReturn(List.of(CAT));

        this.mockMvc.perform(get("/api/v1/cats")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(CAT.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(CAT.getName())));
    }
}