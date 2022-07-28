package com.lufthansa.backend.service;


import com.lufthansa.backend.exception.CustomException;
import com.lufthansa.backend.exception.EntityNotFoundException;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.exception.UnauthorizedException;
import com.lufthansa.backend.model.Dish;
import com.lufthansa.backend.model.Menu;
import com.lufthansa.backend.model.Restaurant;
import com.lufthansa.backend.model.User;
import com.lufthansa.backend.repository.DishRepository;
import com.lufthansa.backend.repository.MenuRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.DishDto;
import com.lufthansa.backend.dto.MenuDto;
import com.lufthansa.backend.repository.RestaurantRepository;
import com.lufthansa.backend.repository.UserRepository;
//import com.lufthansa.backend.util.Interval;
import com.lufthansa.backend.util.Interval;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class MenuService {

    private static final Logger logger = LogManager.getLogger(MenuService.class);

    private final DtoConversion dtoConversion;
    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    @Autowired
    private final Interval interval;

    public MenuDto save(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setMenuName(menuDto.getMenuName());
        menu.setMenuDescription(menuDto.getMenuDescription());
        menu.setMenuOpeningTime(menuDto.getMenuOpeningTime());
        menu.setMenuClosingTime(menuDto.getMenuClosingTime());
        menu.setRestaurantId(menuDto.getRestaurantId());
        menu.setRestaurantName(menuDto.getRestaurantName());
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menuDto.getRestaurantId());
        Restaurant restaurant = restaurantOptional.get();

        Set<Menu> menuList = restaurant.getMenus();
        for (Menu menuClash : restaurant.getMenus()) {
//            Optional<Menu> menuOptional = menuRepository.findById(menuClash.getId());
            if (interval.overlaps(menu.getMenuOpeningTime(),
                                    menu.getMenuClosingTime(),
                                            menuClash.getMenuOpeningTime(),
                                            menuClash.getMenuClosingTime())){
                logger.warn("Menu cannot be created. Times overlap with a different menu.");
                throw new CustomException("Menu cannot be created. Times overlap with a different menu.", HttpStatus.NOT_ACCEPTABLE);
            }
        }

        logger.info("Saving menu.");
        return dtoConversion.convertMenu(menuRepository.save(menu));
    }

    public void deleteById(Integer id) {
        Optional<Menu> menuOptional = menuRepository.findById(id);
        Menu menu = menuOptional.get();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menu.getRestaurantId());
        Restaurant restaurant = restaurantOptional.get();

        if (user.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to edit this menu.");
            throw new UnauthorizedException("You do not have access to edit this menu");
        }
        logger.info("Deleting menu.");
        menuRepository.deleteById(id);
    }

    public MenuDto findById(Integer id) {
        logger.info("Finding menu.");
        return dtoConversion.convertMenu(menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find menu with id: " + id)));
    }

    public List<MenuDto> findAll() {
        logger.info("Retrieving all menus.");
        List<MenuDto> menuDtos = menuRepository.findAll()
                .stream()
                .map(dtoConversion::convertMenu)
                .collect(Collectors.toList());

        System.out.println(menuDtos);
        return menuDtos;
    }

    public MenuDto update(MenuDto menuDto, Integer id) {
        Optional<Menu> menuOptional = menuRepository.findById(id);
        Menu menu = menuOptional.get();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menu.getRestaurantId());
        Restaurant restaurant = restaurantOptional.get();

        Set<Menu> menuList = restaurant.getMenus();
        for (Menu menuClash : restaurant.getMenus()) {
//            Optional<Menu> menuOptional = menuRepository.findById(menuClash.getId());
            if (interval.overlaps(menuDto.getMenuOpeningTime(),
                    menuDto.getMenuClosingTime(),
                    menuClash.getMenuOpeningTime(),
                    menuClash.getMenuClosingTime())){
                logger.warn("Menu cannot be created. Times overlap with a different menu.");
                throw new CustomException("Menu cannot be created. Times overlap with a different menu.", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        if(user == null){
            logger.error("This user does not exist");
            throw new EntityNotFoundException("This user does not exist");
        }

        if (user.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to edit this menu.");
            throw new UnauthorizedException("You do not have access to edit this menu");
        }
        logger.info("Updating restaurant.");

        menu.setMenuName(menuDto.getMenuName());
        menu.setRestaurantName(menuDto.getRestaurantName());
        menu.setMenuDescription(menuDto.getMenuDescription());
        menu.setMenuOpeningTime(menuDto.getMenuOpeningTime());
        menu.setMenuClosingTime(menuDto.getMenuClosingTime());
        menu.setRestaurantId(menuDto.getRestaurantId());
        Set<Dish> dishSet = menu.getDishes();
        for (DishDto dishDto : menuDto.getDishes()) {
            Optional<Dish> dishOptional = dishRepository.findById(dishDto.getId());
            if (dishOptional.isPresent()) {
                Dish dish = dishOptional.get();
                dishSet.add(dish);
            }
        }

        menu.setDishes(dishSet);
        return dtoConversion.convertMenu(menuRepository.save(menu));
    }

    public List<MenuDto> findByRestaurantId(Integer id) {
        logger.info("Getting active menus by restaurant id");
        return menuRepository.getActiveMenusByRestaurantId(id).stream().map(dtoConversion::convertMenu).collect(Collectors.toList());
    }

    public List<MenuDto> findAllByRestaurantId(Integer id) {
        logger.info("Getting menus by restaurant id");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        if (user.getRestaurantId() != id){
            logger.error("You do not have access to this restaurant.");
            throw new UnauthorizedException("You do not have access to these menus");
        }
        return menuRepository.getMenusByRestaurantId(id).stream().map(dtoConversion::convertMenu).collect(Collectors.toList());
    }

}
