package com.lufthansa.webapp.controller;


import com.lufthansa.backend.dto.UserDto;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.lufthansa.backend.model.Role;
import com.lufthansa.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.lufthansa.backend.repository.UserRepository;
import com.lufthansa.backend.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;


    @PostMapping("/add")
    public ResponseEntity<UserDto> add(@Valid @RequestBody UserDto user, BindingResult bindingResult) {

        return ResponseEntity.ok(userService.save(user));

    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
    }


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER','ROLE_CLIENT')")
    public ResponseEntity<UserDto> update(@Valid @PathVariable Integer id, @RequestBody UserDto user, BindingResult bindingResult) {
        return ResponseEntity.ok(userService.update(user, id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER','ROLE_CLIENT')")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    @PutMapping("/make-manager/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> makeManager(@PathVariable Integer id, @RequestBody UserDto user) {
        UserDto userManager = userService.findById(user.getId());
        userManager.setRestaurantId(id);
        userManager.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_RESTAURANT_MANAGER)));
        return ResponseEntity.ok(userService.save(userManager));
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> findUsersByRole(@PathVariable("id") String id) {
        List<User> users = userService.findByRole(id);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }

    private final UserRepository userRepository;

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<UserDto> findUsersByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/ROLE_CLIENT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findClientUsers() {
        return userService.findClientUsers();
    }

    @GetMapping("/ROLE_RESTAURANT_MANAGER")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findManagerUsers() {
        return userService.findManagerUsers();
    }

    @GetMapping("/ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findAdminUsers() {
        return userService.findAdminUsers();
    }

    @RequestMapping(
            value = "/get",
            params = { "page", "size" },
            method = RequestMethod.GET
    )
    public Page<User> findPaginated(
            @RequestParam("page") int page, @RequestParam("size") int size) {

        Page<User> resultPage = userService.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException("There is no more users on this page.");
        }

        return resultPage;
    }
}
