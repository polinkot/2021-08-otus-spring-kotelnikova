package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Owner;
import ru.otus.pk.spring.service.OwnerService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с хозяевами должен ")
@WebMvcTest(controllers = OwnerController.class)
public class OwnerControllerTest {

    private static final Owner OWNER = new Owner(1L, "name1", 35, "address1", "89101112233");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService service;

    @DisplayName("возвращать ожидаемый список хозяев")
    @Test
    public void shouldReturnExpectedOwnersList() throws Exception {
        given(service.findAll()).willReturn(List.of(OWNER));

        this.mockMvc.perform(get("/api/v1/owners")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(OWNER.getName())))
                .andExpect(jsonPath("$[0].age", is(OWNER.getAge())))
                .andExpect(jsonPath("$[0].address", is(OWNER.getAddress())))
                .andExpect(jsonPath("$[0].phone", is(OWNER.getPhone())));
    }
}