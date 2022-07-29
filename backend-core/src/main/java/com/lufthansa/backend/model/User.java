package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@SQLDelete(sql = "UPDATE User SET active = false WHERE user_id=?")
@Where(clause = "active=true")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_id")
    List<Role> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_details", joinColumns = @JoinColumn(name = "id"))
    private Set<UserDetails> userDetails = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Set<Order> orders = new HashSet<>();

    @Column(name = "restaurant_id")
    @JoinColumn(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active = Boolean.TRUE;

//    @OneToOne
//    @JoinColumn(name="restaurant_id", referencedColumnName = "manager_id")
//    private Restaurant restaurant;


}
