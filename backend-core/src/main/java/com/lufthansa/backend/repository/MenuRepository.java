package com.lufthansa.backend.repository;

import com.lufthansa.backend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    // Query to filter out the menus that are not active
    @Query(value = "SELECT * FROM menu m WHERE m.restaurant_id=:id\n" +
            "AND ((m.menu_opening_time < m.menu_closing_time AND NOW() BETWEEN m.menu_opening_time AND m.menu_closing_time)\n" +
            "  OR\n" +
            "  (m.menu_closing_time < m.menu_opening_time AND NOW() < m.menu_opening_time AND NOW() < m.menu_closing_time)\n" +
            "  OR\n" +
            "  (m.menu_closing_time < m.menu_opening_time AND NOW() > m.menu_opening_time))", nativeQuery = true)
    List<Menu> getActiveMenusByRestaurantId(@Param("id") Integer id);

    // Query to retrieve all menus that belong to a restaurant

    @Query(value = "SELECT * FROM menu m WHERE m.restaurant_id=:id AND m.active=true", nativeQuery = true)
    List<Menu> getMenusByRestaurantId(@Param("id") Integer id);
}
