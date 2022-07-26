package com.lufthansa.backend.repository;


import com.lufthansa.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Query to retrieve all the orders belonging to a restaurant
    @Query(value = "SELECT orders.* FROM orders\n" +
            "INNER JOIN restaurant ON orders.restaurant_id = restaurant.restaurant_id\n" +
            "WHERE restaurant.restaurant_id =:user_id", nativeQuery = true)
    List<Order> getOrdersByRestaurantId(@Param("user_id") Integer user_id);

    // Query to retrieve all the orders made by a user
    @Query(value = "SELECT orders.* FROM orders\n" +
            "INNER JOIN user ON user.user_id = orders.user_id\n" +
            "WHERE user.user_id=:user_id", nativeQuery = true)
    List<Order> getOrdersByUserId(@Param("user_id") Integer user_id);

    Order findOrderById(Integer id);

    Order findByOrderNumber(String orderNumber);
}
