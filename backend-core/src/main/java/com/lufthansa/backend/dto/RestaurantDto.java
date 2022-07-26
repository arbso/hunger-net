package com.lufthansa.backend.dto;


import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class RestaurantDto {

    private Integer id;

    private String restaurantName;

    private String restaurantPhone;

    private String restaurantEmail;

    private Integer managerId;

    private Set<MenuDto> menus = new HashSet<>();

}
