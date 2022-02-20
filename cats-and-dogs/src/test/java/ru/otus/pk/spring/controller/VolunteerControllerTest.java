package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Volunteer;
import ru.otus.pk.spring.service.VolunteerService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с волонтёрами должен ")
@WebMvcTest(controllers = VolunteerController.class)
public class VolunteerControllerTest {

    private static final Volunteer VOLUNTEER = new Volunteer(1L, "Volunteer1", "89107776655");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerService service;

    @DisplayName("возвращать ожидаемый список волонтёров")
    @Test
    public void shouldReturnExpectedVolunteersList() throws Exception {
        given(service.findAll()).willReturn(List.of(VOLUNTEER));

        this.mockMvc.perform(get("/api/v1/volunteers")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(VOLUNTEER.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(VOLUNTEER.getName())))
                .andExpect(jsonPath("$[0].phone", is(VOLUNTEER.getPhone())));
    }
}