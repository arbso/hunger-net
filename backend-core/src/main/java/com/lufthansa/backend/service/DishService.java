package com.lufthansa.backend.service;


import com.lufthansa.backend.exception.EmptyFieldException;
import com.lufthansa.backend.exception.EntityNotFoundException;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.exception.UnauthorizedException;
import com.lufthansa.backend.model.*;
import com.lufthansa.backend.repository.DishRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.DishDto;
import com.lufthansa.backend.repository.MenuRepository;
import com.lufthansa.backend.repository.RestaurantRepository;
import com.lufthansa.backend.repository.UserRepository;
import com.lufthansa.backend.util.ActiveMenu;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Objects;
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

    private final ActiveMenu activeMenu;

    private final UserRepository userRepository;

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

        if (dishDto.getMenuId()==null){
            logger.warn("You did not provide a menu ID.");
            throw new EmptyFieldException("You did not provide a menu ID.");
        }
        Optional<Menu> menuOptional = menuRepository.findById(dishDto.getMenuId());

        if(menuOptional.isEmpty()){
            logger.warn("This menu does not exist!");
            throw new EntityNotFoundException("This menu with the ID: "+dishDto.getMenuId()+ " does not exist.");
        }
        Menu menu = menuOptional.get();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menu.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            logger.warn("The restaurant associated with this dish does not exist!");
            throw new EntityNotFoundException("The restaurant associated with this dish does not exist!");
        }
        Restaurant restaurant = restaurantOptional.get();

        if (user.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to edit this dish.");
            throw new UnauthorizedException("You do not have access to edit this dish");
        }




        Optional<Dish> dishOptional = dishRepository.findById(id);
        Dish dish = dishOptional.get();
        dish.setDishName(dishDto.getDishName());
        dish.setDishDescription(dishDto.getDishDescription());
        dish.setDishPrice(dishDto.getDishPrice());
        dish.setMenuId(dishDto.getMenuId());
        logger.info("Updating dish.");
        return dtoConversion.convertDish(dishRepository.save(dish));
    }

    public Dish deleteById(Integer id) {
        logger.info("Deleting dish.");

        Optional<Dish> dishOptional = dishRepository.findById(id);
        if(dishOptional.isEmpty()){
            logger.warn("The dish with the ID: "+id+ " does not exist.");
            throw new UnauthorizedException("The dish with the ID: "+id+ " does not exist.");
        }
        Dish dish = dishOptional.get();

        Optional<Menu> menuOptional = menuRepository.findById(dish.getMenuId());

        if(menuOptional.isEmpty()){
            logger.warn("This menu does not exist!");
            throw new EntityNotFoundException("This menu with the ID: "+dish.getMenuId()+ " does not exist.");
        }
        Menu menu = menuOptional.get();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menu.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            logger.warn("The restaurant associated with this dish does not exist!");
            throw new EntityNotFoundException("The restaurant associated with this dish does not exist!");
        }
        Restaurant restaurant = restaurantOptional.get();

        if (user.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to delete this dish.");
            throw new UnauthorizedException("You do not have access to delete this dish");
        }
        dishRepository.deleteById(id);
        return dish;
    }

    public Dish findById(Integer id) {
        logger.info("Finding dish.");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        Optional<Dish> dishOptional = dishRepository.findById(id);
        if(dishOptional.isEmpty()){
            logger.warn("This dish with the ID: "+id+ " does not exist.");
            throw new UnauthorizedException("This dish with the ID: "+id+ " does not exist.");
        }
        Dish dish = dishOptional.get();

        Optional<Menu> menuOptional = menuRepository.findById(dish.getMenuId());
        if(menuOptional.isEmpty()){
            logger.warn("This dish with the ID: "+id+ " does not exist.");
            throw new UnauthorizedException("This dish with the ID: "+id+ " does not exist.");
        }
        Menu menu = menuOptional.get();


        if(!activeMenu.isActive(menu.getMenuOpeningTime(),menu.getMenuClosingTime())){
            if(user.getRoles().contains(Role.ROLE_ADMIN)){
                return dishRepository.findById(id).orElseThrow(()
                        -> new ResourceNotFoundException("Could not find dish with id: " + id));
            }
            if(Objects.equals(user.getRestaurantId(), menu.getRestaurantId())){
                return dishRepository.findById(id).orElseThrow(()
                        -> new ResourceNotFoundException("Could not find dish with id: " + id));
            }
            logger.warn("You do not have access to view this menu as it is inactive.");
            throw new UnauthorizedException("You do not have access to view this menu as it is inactive.");
        }




        return dishRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Could not find dish with id: " + id));
    }

    public List<DishDto> findAll() {
        logger.info("Retrieving all dishes.");
        return dishRepository.findAll().stream().map(dtoConversion::convertDish).collect(Collectors.toList());
    }

    public List<DishDto> findByMenuId(Integer id) {
        logger.info("Getting dishes by menu id");

        Optional<Menu> menuOptional = menuRepository.findById(id);

        if(menuOptional.isEmpty()){
            logger.warn("This menu does not exist!");
            throw new EntityNotFoundException("This menu with the ID: "+id+ " does not exist.");
        }
        Menu menu = menuOptional.get();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menu.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            logger.warn("The restaurant associated with this menu does not exist!");
            throw new EntityNotFoundException("The restaurant associated with this menu does not exist!");
        }
        Restaurant restaurant = restaurantOptional.get();

        if (user.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to view these dishes as they are inactive.");
            throw new UnauthorizedException("You do not have access to view these dishes as they are inactive");
        }


        return dishRepository.getDishesByMenuId(id).stream().map(dtoConversion::convertDish).collect(Collectors.toList());
    }


}
