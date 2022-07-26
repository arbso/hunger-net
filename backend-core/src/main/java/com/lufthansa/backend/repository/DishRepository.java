package com.lufthansa.backend.repository;


import com.lufthansa.backend.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DishRepository extends JpaRepository<Dish, Integer> {

    Dish getDishById(Integer id);
    @Query(value="SELECT * FROM dish d WHERE d.menu_id=:id", nativeQuery = true)
    List<Dish> getDishesByMenuId(@Param("id") Integer id);
}
