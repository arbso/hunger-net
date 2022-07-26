package com.lufthansa.backend.service;


import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.model.Dish;
import com.lufthansa.backend.model.Menu;
import com.lufthansa.backend.repository.DishRepository;
import com.lufthansa.backend.repository.MenuRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.DishDto;
import com.lufthansa.backend.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private final DishRepository dishRepository;

    public MenuDto save(MenuDto menuDto){
        Menu menu = new Menu();
        menu.setMenuName(menuDto.getMenuName());
        menu.setMenuDescription(menuDto.getMenuDescription());
        menu.setMenuOpeningTime(menuDto.getMenuOpeningTime());
        menu.setMenuClosingTime(menuDto.getMenuClosingTime());
        menu.setRestaurantId(menuDto.getRestaurantId());
        menu.setRestaurantName(menuDto.getRestaurantName());
        logger.info("Saving menu.");
        return dtoConversion.convertMenu(menuRepository.save(menu));
    }

    public void deleteById(Integer id){
        logger.info("Deleting menu.");
        menuRepository.deleteById(id);
    }

    public MenuDto findById(Integer id){
        logger.info("Finding menu.");
        return dtoConversion.convertMenu(menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find menu with id: " + id)));
    }

    public List<MenuDto> findAll(){
        logger.info("Retrieving all menus.");
        List<MenuDto> menuDtos = menuRepository.findAll()
                .stream()
                .map(dtoConversion::convertMenu)
                .collect(Collectors.toList());

        System.out.println(menuDtos);
        return menuDtos;
    }

    public MenuDto update(MenuDto menuDto, Integer id){
        logger.info("Updating restaurant.");
        Optional<Menu> menuOptional = menuRepository.findById(id);
        Menu menu = menuOptional.get();
        menu.setMenuName(menuDto.getMenuName());
        menu.setRestaurantName(menuDto.getRestaurantName());
        menu.setMenuDescription(menuDto.getMenuDescription());
        menu.setMenuOpeningTime(menuDto.getMenuOpeningTime());
        menu.setMenuClosingTime(menuDto.getMenuClosingTime());
        menu.setRestaurantId(menuDto.getRestaurantId());
        Set<Dish> dishSet = menu.getDishes();
        for(DishDto dishDto : menuDto.getDishes() ){
            Optional<Dish> dishOptional = dishRepository.findById(dishDto.getId());
            if(dishOptional.isPresent()){
                Dish dish = dishOptional.get();
                dishSet.add(dish);
            }
        }

        menu.setDishes(dishSet);
        return dtoConversion.convertMenu(menuRepository.save(menu));
    }
    public List<MenuDto> findByRestaurantId(Integer id){
        logger.info("Getting active menus by restaurant id");
        return menuRepository.getActiveMenusByRestaurantId(id).stream().map(dtoConversion::convertMenu).collect(Collectors.toList());
    }

    public List<MenuDto> findAllByRestaurantId(Integer id){
        logger.info("Getting menus by restaurant id");
        return menuRepository.getMenusByRestaurantId(id).stream().map(dtoConversion::convertMenu).collect(Collectors.toList());
    }

}
