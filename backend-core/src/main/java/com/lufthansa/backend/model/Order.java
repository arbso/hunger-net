package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Integer id;

    @Column(name = "orders_address")
    private String orderAddress;

    @CreationTimestamp
    @Column(name = "orders_date")
    private Date orderDate;

    @Column(name = "orders_status")
    private OrderStatus orderStatus;

    @Column(name = "orders_number")
    private String orderNumber;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name="quantity")
    private Integer quantity;

    @OneToOne
    private Dish dish;

    @Column(name="user_id")
    @JoinColumn(name="user_id")
    private Integer userId;

    @Column(name="restaurant_id")
    @JoinColumn(name="restaurant_id")
    private Integer restaurantId;


}