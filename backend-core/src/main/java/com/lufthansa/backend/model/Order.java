package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@SQLDelete(sql = "UPDATE Menu SET active = false WHERE orders_id=?")
@Where(clause = "active=true")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Integer id;

    @NotEmpty(message = "Order Address can not be empty.")
    @Column(name = "orders_address")
    private String orderAddress;

    @CreationTimestamp
    @Column(name = "orders_date")
    private Date orderDate;

    @Column(name = "orders_status")
    private OrderStatus orderStatus;

    @Column(name = "orders_number")
    private String orderNumber;

    @NotNull(message = "Total price can not be empty.")
    @Min(0)
    @Column(name = "total_price")
    private double totalPrice;

    @NotNull(message = "Restaurant Name can not be null.")
    @Column(name = "restaurant_name")
    private String restaurantName;

    @NotNull(message = "Dish Name can not be null.")
    @Column(name = "dish_name")
    private String dishName;

    @Min(1)
    @NotNull(message = "Quantity can not be null.")
    @Column(name="quantity")
    private Integer quantity;

    @NotNull(message = "Dish can not be null.")
    @OneToOne
    private Dish dish;

    @NotNull(message = "User ID can not be null.")
    @Column(name="user_id")
    @JoinColumn(name="user_id")
    private Integer userId;

    @NotNull(message = "Restaurant ID can not be null.")
    @Column(name="restaurant_id")
    @JoinColumn(name="restaurant_id")
    private Integer restaurantId;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active = Boolean.TRUE;

}