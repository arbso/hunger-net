package com.lufthansa.backend.repository;

import com.lufthansa.backend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query(value ="SELECT restaurant.* FROM restaurant\n" +
            "INNER JOIN user ON restaurant.restaurant_id=user.restaurant_id\n" +
            "WHERE username=:username", nativeQuery = true)
    List<Restaurant> findRestaurantsByUserId(@Param("username") String username);

    Restaurant findByRestaurantName(String restaurantName);
    boolean existsByRestaurantName(String restaurantName);
}
