package ru.otus.pk.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.pk.spring.controller.Utils.asJsonString;

@DisplayName("Контроллер для работы с хозяевами. ")
@WebMvcTest(controllers = OwnerController.class)
public class OwnerControllerTest {

    private static final Owner OWNER = new Owner(1L, "owner1", 35, "address1", "89101112233");
    public static final String OWNERS_URL = "/api/v1/owners";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private OwnerService service;

    @DisplayName("для авторизованного возвращать ожидаемый список хозяев")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void returnList() throws Exception {
        given(service.findAll()).willReturn(List.of(OWNER));

        this.mockMvc.perform(get(OWNERS_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(OWNER.getName())))
                .andExpect(jsonPath("$[0].age", is(OWNER.getAge())))
                .andExpect(jsonPath("$[0].address", is(OWNER.getAddress())))
                .andExpect(jsonPath("$[0].phone", is(OWNER.getPhone())));
    }

    @DisplayName("для неавторизованного не возвращать ожидаемый список хозяев")
    @Test
    public void notReturnList() throws Exception {
        given(service.findAll()).willReturn(List.of(OWNER));

        this.mockMvc.perform(get(OWNERS_URL)).andDo(print()).andExpect(status().isFound());
    }

    @DisplayName("для авторизованного возвращать хозяина по id")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void returnById() throws Exception {
        given(service.findById(OWNER.getId())).willReturn(OWNER);

        this.mockMvc.perform(get("/api/v1/owners/" + OWNER.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$.name", is(OWNER.getName())));
    }

    @DisplayName("для неавторизованного не возвращать хозяина по id")
    @Test
    public void notReturnById() throws Exception {
        given(service.findById(OWNER.getId())).willReturn(OWNER);

        this.mockMvc.perform(get("/api/v1/owners/" + OWNER.getId()))
                .andDo(print()).andExpect(status().isFound());
    }

    @DisplayName("авторизованный может добавлять хозяина")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void addForUser() throws Exception {
        given(service.save(any(Owner.class))).willReturn(OWNER);

        this.mockMvc.perform(post(OWNERS_URL).with(csrf())
                .contentType(APPLICATION_JSON)
                .content(asJsonString(OWNER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$.name", is(OWNER.getName())));
    }

    @DisplayName("неавторизованный не может добавлять хозяина")
    @Test
    public void notAddForNotAuth() throws Exception {
        given(service.save(any(Owner.class))).willReturn(OWNER);

        this.mockMvc.perform(post(OWNERS_URL)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(OWNER)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("авторизованный может редактировать хозяина")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    public void updateForUser() throws Exception {
        given(service.findById(anyLong())).willReturn(OWNER);
        given(service.save(any(Owner.class))).willReturn(OWNER);

        this.mockMvc.perform(put(OWNERS_URL).with(csrf())
                .contentType(APPLICATION_JSON)
                .content(asJsonString(OWNER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(OWNER.getId().intValue())))
                .andExpect(jsonPath("$.name", is(OWNER.getName())));
    }

    @DisplayName("неавторизованный не может редактировать хозяина")
    @Test
    public void notUpdateForNotAuth() throws Exception {
        given(service.findById(anyLong())).willReturn(OWNER);
        given(service.save(any(Owner.class))).willReturn(OWNER);

        this.mockMvc.perform(put(OWNERS_URL)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(OWNER)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("админ может удалять хозяина")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void deleteForAdmin() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/owners/" + OWNER.getId()).with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("не админ не может удалять хозяина")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void notDeleteForUser() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/owners/" + OWNER.getId()).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @DisplayName("неавторизованный не может удалять хозяина")
    @Test
    void notDeleteForNotAuth() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        this.mockMvc.perform(delete("/api/v1/owners/" + OWNER.getId()))
                .andExpect(status().isForbidden());
    }
}