package com.lufthansa.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lufthansa.backend.AbstractTest;
import com.lufthansa.backend.model.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.lufthansa.backend.repository.MenuRepository;

import java.util.Calendar;
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
public class MenuControllerTest extends AbstractTest {
    @MockBean
    private MenuRepository menuRepository;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateMenuAndStatus200() throws Exception {
        Integer id = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 15);
        System.out.println(calendar.getTime());
        Menu menu = new Menu();
        menu.setId(id);
        menu.setMenuName("Mengjesi");
        menu.setMenuDescription("Gjera tmira");
        mockMvc.perform(post("/menus/add").with(user("admin").password("a").roles("RESTAURANT_MANAGER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.menuName").value(menu.getMenuName()))
                .andExpect(jsonPath("$.menuDescription").value(menu.getMenuDescription()))
                .andDo(print());
    }

    @Test
    public void shouldReturnMenuAndStatus200() throws Exception {
        Integer id = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 15);
        Menu menu = new Menu();
        menu.setId(id);
        menu.setMenuName("Mengjesi");
        menu.setMenuDescription("Gjera tmira");
        menu.setMenuOpeningTime(calendar.getTime());
        menu.setMenuClosingTime(calendar.getTime());
        when(menuRepository.findById(id)).thenReturn(Optional.of(menu));
        mockMvc.perform(get("/menus/{id}", id).with(user("admin").password("a").roles("RESTAURANT_MANAGER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.menuName").value(menu.getMenuName()))
                .andExpect(jsonPath("$.menuDescription").value(menu.getMenuDescription()))
                .andDo(print());
    }

    @Test
    public void shouldDeleteRestaurantAndStatus200() throws Exception {
        Integer id = 6;
        doNothing().when(menuRepository).deleteById(id);
        mockMvc.perform(delete("/menus/delete/{id}", id).with(user("admin").password("a").roles("RESTAURANT_MANAGER","ADMIN")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateRestaurantAndStatus200() throws Exception {
        Integer id = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 15);
        Menu menu = new Menu();
        menu.setId(id);
        menu.setMenuName("Mengjesi");
        menu.setMenuDescription("Gjera tmira");
        menu.setMenuOpeningTime(calendar.getTime());
        menu.setMenuClosingTime(calendar.getTime());
        Menu updatedMenu = new Menu();
        menu.setId(id);
        menu.setMenuName("Darka");
        menu.setMenuDescription("Gjera tmira");
        menu.setMenuOpeningTime(calendar.getTime());
        menu.setMenuClosingTime(calendar.getTime());
        when(menuRepository.findById(id)).thenReturn(Optional.of(menu));
        when(menuRepository.save(any(Menu.class))).thenReturn(updatedMenu);
        mockMvc.perform(put("/menus/update/{id}", id).with(user("xhoxha").password("12345678").roles("RESTAURANT_MANAGER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMenu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menuName").value(updatedMenu.getMenuName()))
                .andExpect(jsonPath("$.menuDescription").value(updatedMenu.getMenuDescription()))
                .andDo(print());
    }
}