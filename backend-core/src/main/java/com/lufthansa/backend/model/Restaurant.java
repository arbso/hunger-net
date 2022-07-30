package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@SQLDelete(sql = "UPDATE Restaurant SET active = false WHERE user_id=?")
@Where(clause = "active=true")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer id;

    @NotEmpty(message = "Restaurant Name can not be empty.")
    @Column(name = "restaurant_name")
    private String restaurantName;

    @NotEmpty(message = "Restaurant Phone Number can not be empty.")
    @Column(name = "restaurant_phone")
    private String restaurantPhone;

    @NotEmpty(message = "Restaurant Email can not be empty.")
    @Column(name = "restaurant_email")
    private String restaurantEmail;

    @Column(name = "manager_id")
    private Integer managerId;

    @OneToMany
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    private Set<Menu> menus = new HashSet<>();

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active = Boolean.TRUE;

}
