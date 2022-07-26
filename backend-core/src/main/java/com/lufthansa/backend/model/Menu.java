package com.lufthansa.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
@SQLDelete(sql = "UPDATE Menu SET active = false WHERE user_id=?")
@Where(clause = "active=true")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer id;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_description")
    private String menuDescription;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    @Column(name = "menu_opening_time")
    private Date menuOpeningTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    @Column(name = "menu_closing_time")
    private Date menuClosingTime;

//    @OneToMany
//    private Set<Dish> dishes = new HashSet<>();

    @Column(name="restaurant_id")
    private Integer restaurantId;

    @Column(name="restaurant_name")
    private String restaurantName;

    @OneToMany
    @JoinColumn(name = "menu_id", referencedColumnName = "menu_id")
    private Set<Dish> dishes = new HashSet<>();

    @Column(name="active", columnDefinition = "boolean default true")
    private boolean active = Boolean.TRUE;
}
