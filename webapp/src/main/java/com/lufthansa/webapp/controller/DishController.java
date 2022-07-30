package com.lufthansa.webapp.controller;


import com.lufthansa.backend.dto.DishDto;
import lombok.RequiredArgsConstructor;
import com.lufthansa.backend.model.Dish;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lufthansa.backend.service.DishService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<DishDto> add(@Valid @RequestBody DishDto dishDto, BindingResult bindingResult) {

        return ResponseEntity.ok(dishService.save(dishDto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<DishDto> update(@PathVariable Integer id, @RequestBody DishDto dishDto) {
        return ResponseEntity.ok(dishService.update(dishDto, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESTAURANT_MANAGER')")
//  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Dish> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(dishService.deleteById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<DishDto>> findAll() {
        List<DishDto> dishDtos = dishService.findAll();
        if (dishDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(dishDtos);
    }

    @GetMapping("/menu/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER', 'ROLE_CLIENT')")
    public ResponseEntity<List<DishDto>> findByMenuId(@PathVariable Integer id) {
        List<DishDto> dishDtos = dishService.findByMenuId(id);
        if (dishDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(dishDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER', 'ROLE_CLIENT')")
    public Dish findDishById(@PathVariable Integer id) {
        return dishService.findById(id);
    }
}
