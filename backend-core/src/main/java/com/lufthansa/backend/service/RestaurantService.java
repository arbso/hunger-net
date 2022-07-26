package com.lufthansa.backend.service;


import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.model.Menu;
import com.lufthansa.backend.model.Restaurant;
import com.lufthansa.backend.repository.MenuRepository;
import com.lufthansa.backend.repository.RestaurantRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.MenuDto;
import com.lufthansa.backend.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final Logger logger = LogManager.getLogger(RestaurantService.class);
    private final RestaurantRepository restaurantRepository;

    private final DtoConversion dtoConversion;
    private final MenuRepository menuRepository;

    public RestaurantDto save(RestaurantDto restaurantDto){
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(restaurantDto.getRestaurantName());
        restaurant.setRestaurantEmail(restaurantDto.getRestaurantEmail());
        restaurant.setRestaurantPhone(restaurantDto.getRestaurantPhone());
        logger.info("Saving restaurant.");
        return dtoConversion.convertRestaurant(restaurantRepository.save(restaurant));
    }

    public RestaurantDto update(RestaurantDto restaurantDto, Integer id){
        logger.info("Updating restaurant.");
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        Restaurant restaurant = restaurantOptional.get();
        restaurant.setRestaurantName(restaurantDto.getRestaurantName());
        restaurant.setRestaurantEmail(restaurantDto.getRestaurantEmail());
        restaurant.setRestaurantPhone(restaurantDto.getRestaurantPhone());
        Set<Menu> menuList = restaurant.getMenus();
        for(MenuDto menuDto : restaurantDto.getMenus() ){
            Optional<Menu> menuOptional = menuRepository.findById(menuDto.getId());
            if(menuOptional.isPresent()){
                Menu menu = menuOptional.get();
                menuList.add(menu);
            }
        }

        restaurant.setMenus(menuList);
        return dtoConversion.convertRestaurant(restaurantRepository.save(restaurant));
    }

    public void deleteById(Integer id){
        logger.info("Deleting restaurant.");
        restaurantRepository.deleteById(id);
    }

    public RestaurantDto findById(Integer id){
        logger.info("Finding restaurant.");
        return dtoConversion.convertRestaurant(restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find restaurant with id: " + id)));

    }

    public List<RestaurantDto> findAll(){
        logger.info("Retrieving all restaurants.");
        return restaurantRepository.findAll().stream().map(dtoConversion::convertRestaurant).collect(Collectors.toList());
    }

    public List<RestaurantDto> findRestaurantsByUserId(String username){
        logger.info("Retrieving restaurants by User Id");
        return restaurantRepository.findRestaurantsByUserId(username).stream().map(dtoConversion::convertRestaurant).collect(Collectors.toList());
    }

}
