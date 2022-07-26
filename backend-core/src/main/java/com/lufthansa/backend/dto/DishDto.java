package com.lufthansa.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
public class DishDto {

    private Integer id;

    private String dishName;

    private String dishDescription;

    private Double dishPrice;

    private Integer menuId;


}