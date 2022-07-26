package com.lufthansa.backend.dto;

public enum RoleDto {

    ROLE_CLIENT, ROLE_RESTAURANT_MANAGER, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }

}
