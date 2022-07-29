package com.lufthansa.backend.service;

import com.lufthansa.backend.exception.EntityNotFoundException;
import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.exception.UnauthorizedException;
import com.lufthansa.backend.model.Order;
import com.lufthansa.backend.model.OrderStatus;
import com.lufthansa.backend.model.Restaurant;
import com.lufthansa.backend.model.User;
import com.lufthansa.backend.repository.OrderRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.OrderDto;
import com.lufthansa.backend.repository.RestaurantRepository;
import com.lufthansa.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class OrderService {

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final UserRepository userRepository;
    private final DtoConversion dtoConversion;
    private final OrderRepository orderRepository;

    private final RestaurantRepository restaurantRepository;

    public OrderDto update(OrderDto orderDto, Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()){
            throw new EntityNotFoundException("Order ID: "+id+ " does not exist. Try a different one.");
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User userAuth = userRepository.findByUsername(authUsername);
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(orderDto.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            throw new EntityNotFoundException("Restaurant ID: "+id+ " does not exist. Try a different one.");
        }
        Restaurant restaurant = restaurantOptional.get();
        if (user.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to update this order.");
            throw new UnauthorizedException("You do not have access to update this order.");
        }

        Order order = orderOptional.get();
        order.setOrderDate(orderDto.getOrderDate());
        order.setOrderAddress(orderDto.getOrderAddress());
        order.setDish(orderDto.getDish());
        order.setDishName(orderDto.getDishName());
        order.setRestaurantName(orderDto.getRestaurantName());
        order.setRestaurantId(orderDto.getRestaurantId());
        order.setQuantity(orderDto.getQuantity());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setUserId(orderDto.getUserId());
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setOrderStatus(orderDto.getOrderStatus());
        logger.info("Updating order.");
        return dtoConversion.convertOrder(orderRepository.save(order));
    }

    public OrderDto save(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderDate(orderDto.getOrderDate());
        order.setOrderAddress(orderDto.getOrderAddress());
        order.setDish(orderDto.getDish());
        order.setDishName(orderDto.getDishName());
        order.setRestaurantName(orderDto.getRestaurantName());
        order.setRestaurantId(orderDto.getRestaurantId());
        order.setQuantity(orderDto.getQuantity());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setUserId(orderDto.getUserId());
        order.setOrderNumber(generateOrderTrackingNumber());
        order.setOrderStatus(OrderStatus.PLACED);
        logger.info("Placing order.");
        return dtoConversion.convertOrder(orderRepository.save(order));
    }

    public void deleteById(Integer id) {
        logger.info("Deleting order");
        orderRepository.deleteById(id);
    }

    public List<OrderDto> findAll() {
        logger.info("Retrieving all orders.");
        return orderRepository.findAll().stream()
                .map(dtoConversion::convertOrder)
                .collect(Collectors.toList());
    }

    public OrderDto findById(Integer id) {
        logger.info("Finding order");
        return dtoConversion.convertOrder(orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find order with id: " + id)));

    }

    public OrderDto findByOrderNumber(String orderNumber) {
        logger.info("Finding order");
        return dtoConversion.convertOrder(orderRepository.findByOrderNumber(orderNumber));
    }


    public List<OrderDto> getOrdersByRestaurantId(Integer id) {
        logger.info("Finding orders by restaurant.");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User user = userRepository.findByUsername(authUsername);
        if (user.getRestaurantId() != id){
            logger.error("You do not have access to this restaurant's orders.");
            throw new UnauthorizedException("You do not have access to this restaurant's orders.");
        }
        return orderRepository.getOrdersByRestaurantId(id).stream()
                .map(dtoConversion::convertOrder)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByUserId(Integer id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User userAuth = userRepository.findByUsername(authUsername);
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("ID: "+id+ " does not exist. Try a different one.");
        }
        User user = userOptional.get();
        if (user.getId() != userAuth.getId()){
            logger.error("You do not have access to view this user's orders.");
            throw new UnauthorizedException("You do not have access to view this user's orders.");
        }
        logger.info("Finding orders by user");
        return orderRepository.getOrdersByUserId(id).stream()
                .map(dtoConversion::convertOrder)
                .collect(Collectors.toList());
    }



}
