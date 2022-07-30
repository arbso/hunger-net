package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(min=4, max=15)
    @NotNull(message = "Username can not be null.")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Password can not be null.")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Email can not be nul.")
    @Email
    @Column(name = "email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_id")
    List<Role> roles;

    @NotNull(message = "User Details can not be null.")
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
