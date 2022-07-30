package com.lufthansa.webapp.controller;

import com.lufthansa.backend.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.lufthansa.backend.service.MenuService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/menus")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<MenuDto> add(@Valid @RequestBody MenuDto menuDto, BindingResult bindingResult) {
        return ResponseEntity.ok(menuService.save(menuDto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<MenuDto> update(@PathVariable Integer id, @RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(menuService.update(menuDto, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
//  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        menuService.deleteById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<MenuDto>> findAll() {
        List<MenuDto> menuDtos = menuService.findAll();
        if (menuDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(menuDtos);
    }

    @GetMapping("/res/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER', 'ROLE_CLIENT')")
    public ResponseEntity<List<MenuDto>> findActiveByRestaurantId(@PathVariable Integer id) {
        List<MenuDto> menuDtos = menuService.findByRestaurantId(id);
        if (menuDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(menuDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER', 'ROLE_CLIENT')")
    public ResponseEntity<MenuDto> findMenuById(@PathVariable Integer id) {
        return ResponseEntity.ok(menuService.findById(id));
    }

    @GetMapping("/res/all/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER', 'ROLE_CLIENT')")
    public ResponseEntity<List<MenuDto>> findAllByRestaurantId(@PathVariable Integer id) {
        return ResponseEntity.ok(menuService.findAllByRestaurantId(id));
    }


}