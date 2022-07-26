package com.lufthansa.backend.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Embeddable
@Table(name = "user_details")
@Getter
@Setter
public class UserDetails {


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_Number")
    private String phoneNumber;


}
