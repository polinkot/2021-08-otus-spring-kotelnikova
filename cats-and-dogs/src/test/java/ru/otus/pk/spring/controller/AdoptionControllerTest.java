package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.service.AdoptionService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.pk.spring.domain.Gender.FEMALE;

@DisplayName("Контроллер для работы с пристройствами должен ")
@WebMvcTest(controllers = AdoptionController.class)
public class AdoptionControllerTest {

    private static final Cat CAT = new Cat(1L, "Cat1", FEMALE, 1, true, true);
    private static final Owner OWNER = new Owner(1L, "name1", 35, "address1", "89101112233");
    private static final Volunteer VOLUNTEER = new Volunteer(1L, "Volunteer1", "89107776655");
    private static final Adoption ADOPTION = new Adoption(1L, LocalDate.now(), CAT, OWNER, VOLUNTEER);

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