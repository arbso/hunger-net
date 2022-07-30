package com.lufthansa.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Data
public class DishDto {

    private Integer id;

    private String dishName;

    private String dishDescription;

    @Min(value = 0, message = "Price can not be a negative number.")
    @Column(name = "dish_price")
    private Double dishPrice;

    private Integer menuId;


}