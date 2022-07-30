package com.lufthansa.backend.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Embeddable
@Table(name = "user_details")
@Getter
@Setter
public class UserDetails {


    @NotEmpty(message = "First Name can not be empty.")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last Name can not be empty.")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Phone Number can not be empty.")
    @Column(name = "phone_Number")
    private String phoneNumber;


}
