package com.lufthansa.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lufthansa.backend.AbstractTest;
import com.lufthansa.backend.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.lufthansa.backend.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc

public class UserControllerTest extends AbstractTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateUser() throws Exception {
        Integer id = 1;
        User user1 = new User();
        user1.setId(id);
        user1.setUsername("Filani");
        user1.setPassword("1");
        user1.setEmail("filani@gmail.com");
        mockMvc.perform(post("/users/add").with(user("admin").password("a").roles("USER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value(user1.getUsername()))
                .andExpect(jsonPath("$.email").value(user1.getEmail()))
                .andDo(print());
    }

    @Test
    public void shouldReturnUserAndStatus200() throws Exception {
        Integer id = 1;
        User user1 = new User();
        user1.setId(id);
        user1.setUsername("Filani");
        user1.setEmail("filani@gmail.com");
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        mockMvc.perform(get("/users/{id}", id).with(user("admin").password("a").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value(user1.getUsername()))
                .andExpect(jsonPath("$.email").value(user1.getEmail()))
                .andDo(print());
    }

    @Test
    public void shouldDeleteUserAndStatus200() throws Exception {
        Integer id = 6;
        doNothing().when(userRepository).deleteById(id);
        mockMvc.perform(delete("/users/delete/{id}", id).with(user("admin").password("a").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateUserAndStatus200() throws Exception {
        Integer id = 1;
        User user1 = new User();
        user1.setId(id);
        user1.setPassword("1");
        user1.setUsername("Filani");
        user1.setEmail("filani@gmail.com");
        User updatedUser = new User();
        updatedUser.setId(id);
        user1.setPassword("2");
        updatedUser.setUsername("FilaniUpdated");
        updatedUser.setEmail("filani@gmail.comUpdated");
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        mockMvc.perform(patch("/users/update/{id}", id).with(user("admin").password("a").roles("USER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()))
                .andDo(print());
    }
}