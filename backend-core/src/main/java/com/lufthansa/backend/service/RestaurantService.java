package com.lufthansa.backend.service;


import com.lufthansa.backend.exception.CustomException;
import com.lufthansa.backend.exception.EmptyFieldException;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.exception.UnauthorizedException;
import com.lufthansa.backend.model.Menu;
import com.lufthansa.backend.model.Restaurant;
import com.lufthansa.backend.model.User;
import com.lufthansa.backend.repository.MenuRepository;
import com.lufthansa.backend.repository.RestaurantRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.MenuDto;
import com.lufthansa.backend.dto.RestaurantDto;
import com.lufthansa.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final Logger logger = LogManager.getLogger(RestaurantService.class);
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final DtoConversion dtoConversion;
    private final MenuRepository menuRepository;

    public RestaurantDto save(RestaurantDto restaurantDto) {

        if(restaurantDto.getRestaurantName()==null){
            logger.warn("Restaurant name cannot be null!");
            throw new EmptyFieldException("Restaurant name cannot be empty!");
        }

        if(restaurantDto.getRestaurantEmail()==null){
            logger.warn("Restaurant email cannot be null!");
            throw new EmptyFieldException("Restaurant email cannot be empty!");
        }

        if(restaurantDto.getRestaurantPhone()==null){
            logger.warn("Restaurant phone cannot be null!");
            throw new EmptyFieldException("Restaurant phone cannot be empty!");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(restaurantDto.getRestaurantName());
        restaurant.setRestaurantEmail(restaurantDto.getRestaurantEmail());
        restaurant.setRestaurantPhone(restaurantDto.getRestaurantPhone());
        logger.info("Saving restaurant.");
        return dtoConversion.convertRestaurant(restaurantRepository.save(restaurant));
    }

    public RestaurantDto update(RestaurantDto restaurantDto, Integer id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);


        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        Restaurant restaurant = restaurantOptional.get();
        if (user.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to edit this restaurant.");
            throw new UnauthorizedException("You do not have access to edit this restaurant");
        }

        restaurant.setRestaurantName(restaurantDto.getRestaurantName());
        restaurant.setRestaurantEmail(restaurantDto.getRestaurantEmail());
        restaurant.setRestaurantPhone(restaurantDto.getRestaurantPhone());
        Set<Menu> menuList = restaurant.getMenus();
        for (MenuDto menuDto : restaurantDto.getMenus()) {
            Optional<Menu> menuOptional = menuRepository.findById(menuDto.getId());
            if (menuOptional.isPresent()) {
                Menu menu = menuOptional.get();
                menuList.add(menu);
            }
        }
        logger.info("Updating restaurant.");
        restaurant.setMenus(menuList);
        return dtoConversion.convertRestaurant(restaurantRepository.save(restaurant));
    }

    public void deleteById(Integer id) {
        logger.info("Deleting restaurant.");
        restaurantRepository.deleteById(id);
    }

    public RestaurantDto findById(Integer id) {
        logger.info("Finding restaurant.");
        return dtoConversion.convertRestaurant(restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find restaurant with id: " + id)));

    }

    public List<RestaurantDto> findAll() {
        logger.info("Retrieving all restaurants.");
        return restaurantRepository.findAll().stream().map(dtoConversion::convertRestaurant).collect(Collectors.toList());
    }

    public List<RestaurantDto> findRestaurantsByUserId(String username) {
        logger.info("Retrieving restaurants by User Id");
        List<Restaurant> restaurants = restaurantRepository.findRestaurantsByUserId(username);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        Integer restaurantId = restaurants.get(0).getId();
        if (user.getRestaurantId() != restaurantId){
            logger.error("You do not have access to this restaurant.");
            throw new UnauthorizedException("You do not have access to this restaurant");
        }
        return restaurantRepository.findRestaurantsByUserId(username).stream().map(dtoConversion::convertRestaurant).collect(Collectors.toList());
    }

}
