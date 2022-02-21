package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Dog;
import ru.otus.pk.spring.service.DogService;

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
import static ru.otus.pk.spring.domain.Gender.MALE;

@DisplayName("Контроллер для работы с собаками должен ")
@WebMvcTest(controllers = DogController.class)
public class DogControllerTest {

    private static final Dog DOG = new Dog(1L, "Dog1", MALE, 2, true, true);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService service;

    @DisplayName("возвращать ожидаемый список собак")
    @Test
    public void shouldReturnExpectedDogsList() throws Exception {
        given(service.findAll()).willReturn(List.of(DOG));

        this.mockMvc.perform(get("/api/v1/dogs")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(DOG.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(DOG.getName())));
    }

    @DisplayName("возвращать ожидаемую собаку по id")
    @Test
    public void shouldReturnExpectedDogById() throws Exception {
        given(service.findById(DOG.getId())).willReturn(DOG);

        this.mockMvc.perform(get("/api/v1/dogs/" + DOG.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(DOG.getId().intValue())))
                .andExpect(jsonPath("$.name", is(DOG.getName())));
    }

    @DisplayName("добавлять собаку")
    @Test
    public void shouldAddDog() throws Exception {
        given(service.save(any(Dog.class))).willReturn(DOG);

        this.mockMvc.perform(post("/api/v1/dogs")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(DOG)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(DOG.getId().intValue())))
                .andExpect(jsonPath("$.name", is(DOG.getName())));
    }

    @DisplayName("редактировать собаку")
    @Test
    public void shouldUpdateDog() throws Exception {
        given(service.findById(anyLong())).willReturn(DOG);
        given(service.save(any(Dog.class))).willReturn(DOG);

        this.mockMvc.perform(put("/api/v1/dogs")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(DOG)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(DOG.getId().intValue())))
                .andExpect(jsonPath("$.name", is(DOG.getName())));
    }

    @DisplayName("удалять собаку")
    @Test
    void shouldDeleteDog() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/dogs/" + DOG.getId()))
                .andExpect(status().isOk());
    }
}