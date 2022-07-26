package com.lufthansa.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class AuthJWT {

    // Class that contains the token, the username and the authorities
    // Useful for the Angular integration

    public String token;

    public String username;

    public List<Role> authorities;

}
