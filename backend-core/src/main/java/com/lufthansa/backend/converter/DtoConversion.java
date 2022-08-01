package com.lufthansa.backend.converter;

import com.lufthansa.backend.dto.*;
import com.lufthansa.backend.model.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoConversion {

    // Methods to convert each class into a DTO class
    public UserDto convertUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setRestaurantId(user.getRestaurantId());
        userDto.setUserDetails(user.getUserDetails()
                .stream().map(this::convertUserDetails)
                .collect(Collectors.toSet()));
        userDto.setOrders(user.getOrders()
                .stream().map(this::convertOrder)
                .collect(Collectors.toSet()));
        return userDto;
    }

    public UserDetailsDto convertUserDetails(UserDetails userDetails) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setFirstName(userDetails.getFirstName());
        userDetailsDto.setLastName(userDetails.getLastName());
        userDetailsDto.setPhoneNumber(userDetails.getPhoneNumber());
        return userDetailsDto;
    }

    public UserDetails convertToUserDetails(Set<UserDetailsDto> userDetailsDto){
        UserDetails userDetails = new UserDetails();

        UserDetailsDto userDetailsDto1 = userDetailsDto.iterator().next();
        userDetails.setFirstName(userDetailsDto1.getFirstName());
        userDetails.setLastName(userDetailsDto1.getLastName());
        userDetails.setPhoneNumber(userDetailsDto1.getPhoneNumber());

        return userDetails;
    }


    public RestaurantDto convertRestaurant(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setRestaurantName(restaurant.getRestaurantName());
        restaurantDto.setRestaurantEmail(restaurant.getRestaurantEmail());
        restaurantDto.setRestaurantPhone(restaurant.getRestaurantPhone());
        restaurantDto.setMenus(restaurant.getMenus()
                .stream().map(this::convertMenu)
                .collect(Collectors.toSet())

        );
        return restaurantDto;
    }

    public MenuDto convertMenu(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setRestaurantId(menu.getRestaurantId());
        menuDto.setRestaurantName(menu.getRestaurantName());
        menuDto.setMenuName(menu.getMenuName());
        menuDto.setMenuDescription(menu.getMenuDescription());
        menuDto.setMenuOpeningTime(menu.getMenuOpeningTime());
        menuDto.setMenuClosingTime(menu.getMenuClosingTime());

        menuDto.setDishes(menu.getDishes()
                .stream().map(this::convertDish)
                .collect(Collectors.toSet())

        );
        return menuDto;
    }

    public DishDto convertDish(Dish dish) {

        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setDishName(dish.getDishName());
        dishDto.setDishDescription(dish.getDishDescription());
        dishDto.setDishPrice(dish.getDishPrice());
        dishDto.setMenuId(dish.getMenuId());
        return dishDto;
    }

    public OrderDto convertOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setOrderAddress(order.getOrderAddress());
        orderDto.setDish(order.getDish());
        orderDto.setDishName(order.getDishName());
        orderDto.setRestaurantName(order.getRestaurantName());
        orderDto.setRestaurantId(order.getRestaurantId());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setUserId(order.getUserId());
        return orderDto;
    }

}
