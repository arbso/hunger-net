package com.lufthansa.backend.service;


import com.lufthansa.backend.exception.EmptyFieldException;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.model.Dish;
import com.lufthansa.backend.repository.DishRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.DishDto;
import com.lufthansa.backend.repository.MenuRepository;
import com.lufthansa.backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class DishService {

    private static final Logger logger = LogManager.getLogger(DishService.class);

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final DtoConversion dtoConversion;
    private final DishRepository dishRepository;

    public DishDto save(DishDto dishDto) {

        if(dishDto.getDishName()==null){
            logger.warn("Dish name cannot be null!");
            throw new EmptyFieldException("Dish name cannot be null!");
        }

        if(dishDto.getDishPrice()==null){
            logger.warn("Dish price cannot be null!");
            throw new EmptyFieldException("Dish price cannot be empty!");
        }

        if(dishDto.getDishDescription()==null){
            logger.warn("Dish description cannot be null!");
            throw new EmptyFieldException("Dish description cannot be empty!");
        }

        Dish dish = new Dish();
        dish.setDishName(dishDto.getDishName());
        dish.setDishDescription(dishDto.getDishDescription());
        dish.setDishPrice(dishDto.getDishPrice());
        dish.setMenuId(dishDto.getMenuId());
        logger.info("Saving dish.");
        return dtoConversion.convertDish(dishRepository.save(dish));
    }

    public DishDto update(DishDto dishDto, Integer id) {
        Optional<Dish> dishOptional = dishRepository.findById(id);
        Dish dish = dishOptional.get();
        dish.setDishName(dishDto.getDishName());
        dish.setDishDescription(dishDto.getDishDescription());
        dish.setDishPrice(dishDto.getDishPrice());
        logger.info("Updating dish.");
        return dtoConversion.convertDish(dishRepository.save(dish));
    }

    public void deleteById(Integer id) {
        logger.info("Deleting dish.");
        dishRepository.deleteById(id);
    }

    public Dish findById(Integer id) {
        logger.info("Finding dish.");
        return dishRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find dish with id: " + id));
    }

    public List<DishDto> findAll() {
        logger.info("Retrieving all menus.");
        return dishRepository.findAll().stream().map(dtoConversion::convertDish).collect(Collectors.toList());
    }

    public List<DishDto> findByMenuId(Integer id) {
        logger.info("Getting dishes by menu id");
        return dishRepository.getDishesByMenuId(id).stream().map(dtoConversion::convertDish).collect(Collectors.toList());
    }


}
