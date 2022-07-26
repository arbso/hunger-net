package com.lufthansa.backend.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_CLIENT, ROLE_RESTAURANT_MANAGER, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }


}
