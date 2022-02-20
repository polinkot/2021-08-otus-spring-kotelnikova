package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Adoption;
import ru.otus.pk.spring.service.AdoptionService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с пристройствами должен ")
@WebMvcTest(controllers = AdoptionController.class)
public class AdoptionControllerTest {

    private static final Adoption ADOPTION = new Adoption(1L);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdoptionService service;

    @DisplayName("возвращать ожидаемый список пристройств")
    @Test
    public void shouldReturnExpectedAdoptionsList() throws Exception {
        given(service.findAll()).willReturn(List.of(ADOPTION));

        this.mockMvc.perform(get("/api/v1/adoptions")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(ADOPTION.getId().intValue())));
    }
}