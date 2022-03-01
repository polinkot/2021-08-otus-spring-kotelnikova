package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.Animal;
import ru.otus.pk.spring.service.AdoptionService;
import ru.otus.pk.spring.service.AnimalService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.pk.spring.controller.Utils.asJsonString;
import static ru.otus.pk.spring.domain.Animal.AnimalStatus.NOT_ADOPTED;
import static ru.otus.pk.spring.domain.Gender.FEMALE;

@DisplayName("Контроллер для работы с животными. ")
@WebMvcTest(controllers = AnimalController.class)
public class AnimalControllerTest {

    public static final String ANIMALS_URL = "/api/v1/animals/";

    private static final Animal CAT = new Animal(1L, "Cat1", FEMALE, 1, true, true, NOT_ADOPTED, Animal.AnimalType.CAT);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AdoptionService adoptionService;

    @MockBean
    private AnimalService service;

    @DisplayName("для авторизованных возвращать ожидаемый список животных")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void shouldReturnExpectedCatsList() throws Exception {
        given(service.findAll()).willReturn(List.of(CAT));

        this.mockMvc.perform(get(ANIMALS_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(CAT.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(CAT.getName())));
    }

    @DisplayName("для всех возвращать животное по id")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void shouldReturnExpectedCatById() throws Exception {
        given(service.findById(CAT.getId())).willReturn(CAT);

        this.mockMvc.perform(get(ANIMALS_URL + CAT.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CAT.getId().intValue())))
                .andExpect(jsonPath("$.name", is(CAT.getName())));
    }

    @DisplayName("авторизованный может добавлять животное")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void addForUser() throws Exception {
        given(service.save(any(Animal.class))).willReturn(CAT);

        this.mockMvc.perform(post(ANIMALS_URL).with(csrf())
                .contentType(APPLICATION_JSON)
                .content(asJsonString(CAT)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CAT.getId().intValue())))
                .andExpect(jsonPath("$.name", is(CAT.getName())));
    }

    @DisplayName("неавторизованный не может добавлять животное")
    @Test
    public void notAddForNotAuth() throws Exception {
        given(service.save(any(Animal.class))).willReturn(CAT);

        this.mockMvc.perform(post(ANIMALS_URL)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(CAT)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("авторизованный может редактировать животное")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void updateForUser() throws Exception {
        given(service.findById(anyLong())).willReturn(CAT);
        given(service.save(any(Animal.class))).willReturn(CAT);

        this.mockMvc.perform(put(ANIMALS_URL).with(csrf())
                .contentType(APPLICATION_JSON)
                .content(asJsonString(CAT)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CAT.getId().intValue())))
                .andExpect(jsonPath("$.name", is(CAT.getName())));
    }

    @DisplayName("неавторизованный не может редактировать животное")
    @Test
    public void notUpdateForNotAuth() throws Exception {
        given(service.findById(anyLong())).willReturn(CAT);
        given(service.save(any(Animal.class))).willReturn(CAT);

        this.mockMvc.perform(put(ANIMALS_URL)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(CAT)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("админ может удалять животное")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void deleteForAdmin() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete(ANIMALS_URL + CAT.getId()).with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("не админ не может удалять животное")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void notDeleteForUser() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete(ANIMALS_URL + CAT.getId()).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @DisplayName("неавторизованный не может удалять животное")
    @Test
    void notDeleteForNotAuth() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete(ANIMALS_URL + CAT.getId()))
                .andExpect(status().isForbidden());
    }
}