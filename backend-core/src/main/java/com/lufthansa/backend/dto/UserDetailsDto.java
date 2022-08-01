package com.lufthansa.backend.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
public class UserDetailsDto {

    private String firstName;

    private String lastName;

    private String phoneNumber;


}
