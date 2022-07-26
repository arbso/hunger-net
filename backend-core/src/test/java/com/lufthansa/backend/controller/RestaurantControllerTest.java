package com.lufthansa.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lufthansa.backend.AbstractTest;
import com.lufthansa.backend.model.Restaurant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.lufthansa.backend.repository.RestaurantRepository;

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
public class RestaurantControllerTest extends AbstractTest {
    @MockBean
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateRestaurantAndStatus200() throws Exception {
        Integer id = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setRestaurantName("Duff");
        restaurant.setRestaurantEmail("email");
        restaurant.setRestaurantPhone("865321");
        mockMvc.perform(post("/restaurants/add").with(user("admin").password("a").roles("USER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.restaurantName").value(restaurant.getRestaurantName()))
                .andExpect(jsonPath("$.restaurantEmail").value(restaurant.getRestaurantEmail()))
                .andExpect(jsonPath("$.restaurantPhone").value(restaurant.getRestaurantPhone()))
                .andDo(print());
    }

    @Test
    public void shouldReturnRestaurantAndStatus200() throws Exception {
        Integer id = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setRestaurantName("Duff");
        restaurant.setRestaurantEmail("email");
        restaurant.setRestaurantPhone("865321");
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        mockMvc.perform(get("/restaurants/{id}", id).with(user("admin").password("a").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.restaurantName").value(restaurant.getRestaurantName()))
                .andExpect(jsonPath("$.restaurantEmail").value(restaurant.getRestaurantEmail()))
                .andExpect(jsonPath("$.restaurantPhone").value(restaurant.getRestaurantPhone()))
                .andDo(print());
    }

    @Test
    public void shouldDeleteRestaurantAndStatus200() throws Exception {
        Integer id = 6;
        doNothing().when(restaurantRepository).deleteById(id);
        mockMvc.perform(delete("/restaurants/delete/{id}", id).with(user("admin").password("a").roles("USER","ADMIN")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateRestaurantAndStatus200() throws Exception {
        Integer id = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setRestaurantName("Duff");
        restaurant.setRestaurantEmail("email");
        restaurant.setRestaurantPhone("865321");
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setId(id);
        updatedRestaurant.setRestaurantName("DuffUPDATE");
        updatedRestaurant.setRestaurantEmail("filani@gmail.comUpdated");
        updatedRestaurant.setRestaurantPhone("f21342567");
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(updatedRestaurant);
        mockMvc.perform(patch("/restaurants/update/{id}", id).with(user("xhoxha").password("12345678").roles("RESTAURANT_MANAGER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRestaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value(updatedRestaurant.getRestaurantName()))
                .andExpect(jsonPath("$.restaurantEmail").value(updatedRestaurant.getRestaurantEmail()))
                .andExpect(jsonPath("$.restaurantPhone").value(updatedRestaurant.getRestaurantPhone()))
                .andDo(print());
    }
}