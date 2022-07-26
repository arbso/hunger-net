package com.lufthansa.backend.service;


import com.lufthansa.backend.exception.CustomException;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.model.Role;
import com.lufthansa.backend.model.User;
import com.lufthansa.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LogManager.getLogger(User.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User save(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
//            user.setActive(true);
            logger.info("Saving user.");
            userRepository.save(user);
            return user;
        } else {
            logger.warn("Username is already in use.");
            throw new CustomException("Username is already in use", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    public User update(User user, Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        User userUpdate = userOptional.get();
        userUpdate.setUsername(user.getUsername());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setUserDetails(user.getUserDetails());
        userUpdate.setRestaurantId(user.getRestaurantId());

        logger.info("Updating user.");
        return userRepository.save(userUpdate);
    }


    public void deleteById(Integer id) {
        logger.info("Deleting user.");
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        logger.info("Retrieving all users.");
        return userRepository.findAllUsersExceptAdmins();
    }


    public User findById(Integer id) {
        logger.info("Finding user.");
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find user with id: " + id));
    }

    public List<User> findByRole(Integer id) {
        logger.info("Find user by role.");
        return userRepository.findUserByRoles(id);
    }

    public List<User> findClientUsers() {
        logger.info("Find user by role.");
        return userRepository.findClientUsers();
    }

    public List<User> findManagerUsers() {
        logger.info("Find user by role.");
        return userRepository.findManagerUsers();
    }

    public List<User> findAdminUsers() {
        logger.info("Find user by role.");
        return userRepository.findAdminUsers();
    }

    public User findByUsername(String username) {
        logger.info("Finding user by username");
        return userRepository.findByUsername(username);
    }

    public Page<User> findPaginated(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }
}
