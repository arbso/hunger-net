package com.lufthansa.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lufthansa.backend.AbstractTest;
import com.lufthansa.backend.model.Dish;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.lufthansa.backend.repository.DishRepository;

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
public class DishControllerTest extends AbstractTest {
    @MockBean
    private DishRepository dishRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateDishAndStatus200() throws Exception {
        Integer id = 1;
        Dish dish = new Dish();
        dish.setId(id);
        dish.setDishName("Groshe");
        dish.setDishDescription("fasule fshati");
        dish.setDishPrice(150.0);
        mockMvc.perform(post("/dishes/add").with(user("admin").password("a").roles("RESTAURANT_MANAGER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dish)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.dishName").value(dish.getDishName()))
                .andExpect(jsonPath("$.dishDescription").value(dish.getDishDescription()))
                .andExpect(jsonPath("$.dishPrice").value(dish.getDishPrice()))
                .andDo(print());
    }

    @Test
    public void shouldReturnRestaurantAndStatus200() throws Exception {
        Integer id = 1;
        Dish dish = new Dish();
        dish.setId(id);
        dish.setDishName("Groshe");
        dish.setDishDescription("fasule fshati");
        dish.setDishPrice(150.0);
        when(dishRepository.findById(id)).thenReturn(Optional.of(dish));
        mockMvc.perform(get("/dishes/{id}", id).with(user("admin").password("a").roles("RESTAURANT_MANAGER","ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.dishName").value(dish.getDishName()))
                .andExpect(jsonPath("$.dishDescription").value(dish.getDishDescription()))
                .andExpect(jsonPath("$.dishPrice").value(dish.getDishPrice()))
                .andDo(print());
    }

    @Test
    public void shouldDeleteRestaurantAndStatus200() throws Exception {
        Integer id = 6;
        doNothing().when(dishRepository).deleteById(id);
        mockMvc.perform(delete("/dishes/delete/{id}", id).with(user("admin").password("a").roles("RESTAURANT_MANAGER","ADMIN")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateRestaurantAndStatus200() throws Exception {
        Integer id = 1;
        Dish dish = new Dish();
        dish.setId(id);
        dish.setDishName("Groshe");
        dish.setDishDescription("fasule fshati");
        dish.setDishPrice(150.0);
        Dish updatedDish = new Dish();
        updatedDish.setId(id);
        updatedDish.setDishName("Pilaf");
        updatedDish.setDishDescription("oriz fshati");
        updatedDish.setDishPrice(250.0);
        when(dishRepository.findById(id)).thenReturn(Optional.of(dish));
        when(dishRepository.save(any(Dish.class))).thenReturn(updatedDish);
        mockMvc.perform(put("/dishes/update/{id}", id).with(user("xhoxha").password("12345678").roles("RESTAURANT_MANAGER","ADMIN")).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDish)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dishName").value(updatedDish.getDishName()))
                .andExpect(jsonPath("$.dishDescription").value(updatedDish.getDishDescription()))
                .andExpect(jsonPath("$.dishPrice").value(updatedDish.getDishPrice()))
                .andDo(print());
    }
}