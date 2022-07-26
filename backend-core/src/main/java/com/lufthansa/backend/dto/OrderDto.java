package com.lufthansa.backend.dto;


import com.lufthansa.backend.model.Dish;
import com.lufthansa.backend.model.OrderStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class OrderDto {

    private Integer id;

    private String orderAddress;

    private Date orderDate;

    private OrderStatus orderStatus;

    private String orderNumber;

    private double totalPrice;

    private String restaurantName;

    private String dishName;

    private Integer quantity;

    private Dish dish;

    private Integer userId;

    private Integer restaurantId;

}