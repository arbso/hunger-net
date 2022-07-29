package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "dish")
@Getter
@Setter
@SQLDelete(sql = "UPDATE Dish SET active = false WHERE dish_id=?")
@Where(clause = "active=true")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Integer id;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "dish_description")
    private String dishDescription;

    @Column(name = "dish_price")
    private Double dishPrice;

    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active = Boolean.TRUE;

}