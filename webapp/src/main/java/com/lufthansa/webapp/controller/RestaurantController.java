package com.lufthansa.webapp.controller;

import com.lufthansa.backend.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.lufthansa.backend.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurants")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<RestaurantDto> add(@RequestBody RestaurantDto restaurantDto) {

        return ResponseEntity.ok(restaurantService.save(restaurantDto));
    }

    @DeleteMapping(value = "/delete/{id}")
//  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        restaurantService.deleteById(id);
    }


    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByUsername(@PathVariable String username) {
        List<RestaurantDto> restaurantDtos = restaurantService.findRestaurantsByUserId(username);
        if (restaurantDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(restaurantDtos);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<RestaurantDto> update(@PathVariable Integer id, @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.update(restaurantDto, id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER', 'ROLE_CLIENT')")
    public ResponseEntity<List<RestaurantDto>> findAllRestaurants() {

        if (restaurantService.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER', 'ROLE_CLIENT')")
    public ResponseEntity<RestaurantDto> findRestaurantById(@PathVariable Integer id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

}
