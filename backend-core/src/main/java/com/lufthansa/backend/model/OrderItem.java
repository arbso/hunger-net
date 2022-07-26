package com.lufthansa.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders_item")
@Getter
@Setter
public class OrderItem {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="orders_item_id")
    private Integer id;

    @Column(name = "orders_item_order")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name="orders_item_quantity")
    private Integer quantity;
}
