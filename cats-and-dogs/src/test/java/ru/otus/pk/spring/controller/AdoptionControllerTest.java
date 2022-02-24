package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.service.AdoptionService;

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
import static ru.otus.pk.spring.domain.Gender.FEMALE;

@DisplayName("Контроллер для работы с пристройствами. ")
@WebMvcTest(controllers = AdoptionController.class)
public class AdoptionControllerTest {

    private static final Cat CAT = new Cat(1L, "Cat1", FEMALE, 1, true, true);
    private static final Owner OWNER = new Owner(1L, "name1", 35, "address1", "89101112233");
    private static final Volunteer VOLUNTEER = new Volunteer(1L, "Volunteer1", "89107776655");
    private static final Adoption ADOPTION = new Adoption(1L, CAT, OWNER, VOLUNTEER);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AdoptionService service;

    @DisplayName("для авторизованного возвращать ожидаемый список пристройств")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void returnList() throws Exception {
        given(service.findAll()).willReturn(List.of(ADOPTION));

        this.mockMvc.perform(get("/api/v1/adoptions")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(ADOPTION.getId().intValue())));
    }

    @DisplayName("для неавторизованного не возвращать список пристройств (редирект на логин)")
    @Test
    public void notReturnList() throws Exception {
        given(service.findAll()).willReturn(List.of(ADOPTION));

        this.mockMvc.perform(get("/api/v1/adoptions")).andDo(print()).andExpect(status().isFound());
    }

    @DisplayName("для авторизованного возвращать пристройство по id")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void returnById() throws Exception {
        given(service.findById(ADOPTION.getId())).willReturn(ADOPTION);

        this.mockMvc.perform(get("/api/v1/adoptions/" + ADOPTION.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(ADOPTION.getId().intValue())))
                .andExpect(jsonPath("$.animal.name", is(ADOPTION.getAnimal().getName())));
    }

    @DisplayName("для неавторизованного не возвращать пристройство по id (редирект на логин)")
    @Test
    public void notReturnById() throws Exception {
        given(service.findById(ADOPTION.getId())).willReturn(ADOPTION);

        this.mockMvc.perform(get("/api/v1/adoptions/" + ADOPTION.getId()))
                .andDo(print()).andExpect(status().isFound());
    }

    @DisplayName("авторизованный может добавлять пристройство")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void addForUser() throws Exception {
        given(service.save(any(Adoption.class))).willReturn(ADOPTION);

        this.mockMvc.perform(post("/api/v1/adoptions").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(asJsonString(ADOPTION)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(ADOPTION.getId().intValue())))
                .andExpect(jsonPath("$.animal.name", is(ADOPTION.getAnimal().getName())));
    }

    @DisplayName("неавторизованный не может добавлять пристройство")
    @Test
    public void notAddForNotAuth() throws Exception {
        given(service.save(any(Adoption.class))).willReturn(ADOPTION);

        this.mockMvc.perform(post("/api/v1/adoptions")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(ADOPTION)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("админ может удалять пристройство")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void deleteForAdmin() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/adoptions/" + ADOPTION.getId()).with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("не админ не может удалять пристройство")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void notDeleteForUser() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/adoptions/" + ADOPTION.getId()).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @DisplayName("неавторизованный не может удалять пристройство")
    @Test
    void notDeleteForNotAuth() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/adoptions/" + ADOPTION.getId()))
                .andExpect(status().isForbidden());
    }
}