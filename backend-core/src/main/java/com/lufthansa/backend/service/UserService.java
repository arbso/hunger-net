package com.lufthansa.backend.service;



import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.MenuDto;
import com.lufthansa.backend.dto.OrderDto;
import com.lufthansa.backend.dto.UserDetailsDto;
import com.lufthansa.backend.dto.UserDto;
import com.lufthansa.backend.exception.EmptyFieldException;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.exception.UnauthorizedException;
import com.lufthansa.backend.model.*;
import com.lufthansa.backend.repository.OrderRepository;
import com.lufthansa.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LogManager.getLogger(User.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    private final DtoConversion dtoConversion;

    public UserDto save(UserDto userDto) {
        Set<UserDetailsDto> ud = userDto.getUserDetails();
        User user = new User();
        if(userDto.getUsername()==null){
            logger.warn("Username cannot be null!");
            throw new EmptyFieldException("Username cannot be null!");
        }

        if(userDto.getEmail()==null){
            logger.warn("Email cannot be null!");
            throw new EmptyFieldException("Empty cannot be empty!");
        }

        if(userDto.getPassword()==null){
            logger.warn("Password cannot be null!");
            throw new EmptyFieldException("Password cannot be null!");
        }

        if(ud.iterator().next().getFirstName()==null
                || ud.iterator().next().getLastName()==null
                || ud.iterator().next().getPhoneNumber()==null){
            logger.warn("First Name, Last Name, or your Phone Number cannot be null!");
            throw new EmptyFieldException("First Name, Last Name, or your Phone Number cannot be null!");
        }

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        Set<UserDetails> userDetailsSet = user.getUserDetails();
        for (UserDetailsDto userDetailsDto : userDto.getUserDetails()) {

                UserDetails userDetails1 = new UserDetails();
                userDetails1.setFirstName(userDetailsDto.getFirstName());
                userDetails1.setLastName(userDetailsDto.getLastName());
                userDetails1.setPhoneNumber(userDetailsDto.getPhoneNumber());
                userDetailsSet.add(userDetails1);
            }


        user.setUserDetails(userDetailsSet);

        if(userDto.getId()!=null){
            return dtoConversion.convertUser(userRepository.save(user));
        }

        if (!userRepository.existsByUsername(userDto.getUsername())) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(userDto.getRestaurantId() != null){
                user.setRestaurantId(userDto.getRestaurantId());
                user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_RESTAURANT_MANAGER)));
            }else{
                user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
            }

            logger.info("Saving user.");
            return dtoConversion.convertUser(userRepository.save(user));
        } else {
            logger.warn("Username is already in use.");
            throw new EmptyFieldException("Username is already in use.");
        }



    }


    public UserDto update(UserDto userDto, Integer id) {

        Optional<User> userOptional = userRepository.findById(id);

        org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User userAuth = userRepository.findByUsername(authUsername);

        User user = userOptional.get();
        if ((user.getId() != userAuth.getId()) && !(userAuth.getRoles().contains(Role.ROLE_ADMIN))){

            logger.error("You do not have access to edit this user.");
            throw new UnauthorizedException("You do not have access to edit this user");
        }


            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            Set<UserDetails> userDetailsSet = user.getUserDetails();
            userDetailsSet.clear();
            for (UserDetailsDto userDetailsDto : userDto.getUserDetails()) {

                UserDetails userDetails1 = new UserDetails();
                userDetails1.setFirstName(userDetailsDto.getFirstName());
                userDetails1.setLastName(userDetailsDto.getLastName());
                userDetails1.setPhoneNumber(userDetailsDto.getPhoneNumber());
                userDetailsSet.add(userDetails1);
            }
            user.setUserDetails(userDetailsSet);


            Set<Order> orderSet = user.getOrders();
            for (OrderDto orderDto : userDto.getOrders()) {
                Optional<Order> orderOptional = orderRepository.findById(orderDto.getId());
                if (orderOptional.isPresent()) {
                    Order order = orderOptional.get();
                    orderSet.add(order);
                }
            }
            user.setOrders(orderSet);

            logger.info("Updating user.");
            return dtoConversion.convertUser(userRepository.save(user));
        }



    public void deleteById(Integer id) {
        logger.info("Deleting user.");
        userRepository.deleteById(id);
    }

    public List<UserDto> findAll() {
        logger.info("Retrieving all users.");
        return userRepository.findAllUsersExceptAdmins().stream().map(dtoConversion::convertUser).collect(Collectors.toList());
    }


    public UserDto findById(Integer id) {
        logger.info("Finding user.");
        Optional<User> userOptional = userRepository.findById(id);

        org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User userAuth = userRepository.findByUsername(authUsername);

        User user = userOptional.get();
        if ((user.getId() != userAuth.getId()) && !(userAuth.getRoles().contains(Role.ROLE_ADMIN))){

            logger.error("You do not have access to view this user.");
            throw new UnauthorizedException("You do not have access to view this user");
        }
        return dtoConversion.convertUser(userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Could not find user with id: " + id)));
    }

    public List<User> findByRole(String id) {
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

    public UserDto findByUsername(String username) {
        logger.info("Finding user by username");
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User userAuth = userRepository.findByUsername(authUsername);

        User user = userOptional.get();
        if ((user.getId() != userAuth.getId()) && !(userAuth.getRoles().contains(Role.ROLE_ADMIN))){

            logger.error("You do not have access to view this user.");
            throw new UnauthorizedException("You do not have access to view this user");
        }
        return dtoConversion.convertUser(userRepository.findByUsername(username));
    }

    public Page<User> findPaginated(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }
}
