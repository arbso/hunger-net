package com.lufthansa.backend.dto;


import com.lufthansa.backend.model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private Integer id;

    private String username;

    private String password;

    private String email;

    List<Role> roles;

    private UserDetailsDto userDetails;

    private Set<OrderDto> orders = new HashSet<>();

    private Integer restaurantId;


}
