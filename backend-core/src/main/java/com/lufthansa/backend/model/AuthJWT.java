package com.lufthansa.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class AuthJWT {

    public String token;

    public String username;

    public List<Role> authorities;

}
