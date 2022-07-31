package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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


    @NotEmpty(message = "Dish name can not be empty.")
    @Column(name = "dish_name")
    private String dishName;

    @NotEmpty(message = "Dish Description can not be empty.")
    @Column(name = "dish_description")
    private String dishDescription;


    @NotNull
    @DecimalMin(value = "0.0", message = "Price can not be a negative number.")
    @Column(name = "dish_price")
    private Double dishPrice;

    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active = Boolean.TRUE;

}