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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.pk.spring.controller.Utils.asJsonString;

@DisplayName("Контроллер для работы с хозяевами должен ")
@WebMvcTest(controllers = OwnerController.class)
public class OwnerControllerTest {

    private static final Owner OWNER = new Owner(1L, "owner1", 35, "address1", "89101112233");

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

    @DisplayName("возвращать хозяина по id")
    @Test
    public void shouldReturnExpectedOwnerById() throws Exception {
        given(service.findById(OWNER.getId())).willReturn(OWNER);

        this.mockMvc.perform(get("/api/v1/owners/" + OWNER.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$.name", is(OWNER.getName())));
    }

    @DisplayName("добавлять хозяина")
    @Test
    public void shouldAddOwner() throws Exception {
        given(service.save(any(Owner.class))).willReturn(OWNER);

        this.mockMvc.perform(post("/api/v1/owners")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(OWNER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$.name", is(OWNER.getName())));
    }

    @DisplayName("редактировать хозяина")
    @Test
    public void shouldUpdateOwner() throws Exception {
        given(service.findById(anyLong())).willReturn(OWNER);
        given(service.save(any(Owner.class))).willReturn(OWNER);

        this.mockMvc.perform(put("/api/v1/owners")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(OWNER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$.name", is(OWNER.getName())));
    }

    @DisplayName("удалять хозяина")
    @Test
    void shouldDeleteOwner() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/owners/" + OWNER.getId()))
                .andExpect(status().isOk());
    }
}