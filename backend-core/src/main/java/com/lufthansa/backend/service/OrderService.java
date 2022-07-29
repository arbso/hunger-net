package com.lufthansa.backend.service;

import com.lufthansa.backend.exception.EmptyFieldException;
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

    public OrderDto update(Integer id, Integer statusNumber) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order order = orderOptional.get();
        if(orderOptional.isEmpty()){
            throw new EntityNotFoundException("Order ID: "+id+ " does not exist. Try a different one.");
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User userAuth = userRepository.findByUsername(authUsername);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(order.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            throw new EntityNotFoundException("Restaurant ID: "+id+ " does not exist. Try a different one.");
        }
        Restaurant restaurant = restaurantOptional.get();
        if (userAuth.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to update this order.");
            throw new UnauthorizedException("You do not have access to update this order.");
        }

        switch(statusNumber){
            case 1:
                if(order.getOrderStatus()!=OrderStatus.PLACED){
                    logger.warn("Order status cannot be set as 'Approved' because it is not on 'Placed' status.");
                    throw new EmptyFieldException("Order status cannot be set as 'Approved' because it is not on 'Placed' status.");
                }
                order.setOrderStatus(OrderStatus.APPROVED);
                break;
            case 2:
                if(order.getOrderStatus()!=OrderStatus.APPROVED){
                    logger.warn("Order status cannot be set as 'Prepared' because it is not on 'Approved' status.");
                    throw new EmptyFieldException("Order status cannot be set as 'Prepared' because it is not on 'Approved' status.");
                }
                order.setOrderStatus(OrderStatus.PREPARED);
                break;
            case 3:
                if(order.getOrderStatus()!=OrderStatus.PREPARED){
                    logger.warn("Order status cannot be set as 'WOD' because it is not on 'Prepared' status.");
                    throw new EmptyFieldException("Order status cannot be set as 'WOD' because it is not on 'Prepared' status.");
                }
                order.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY);
                break;
            case 4:
                if(order.getOrderStatus()!=OrderStatus.WAITING_FOR_DELIVERY){
                    logger.warn("Order status cannot be set as 'Delivered' because it is not on 'WOD' status.");
                    throw new EmptyFieldException("Order status cannot be set as 'Delivered' because it is not on 'WOD' status.");
                }
                order.setOrderStatus(OrderStatus.DELIVERED);
                break;
            case 5:
                if(order.getOrderStatus()!=OrderStatus.PLACED){
                    logger.warn("Order status cannot be set as 'Rejected' because it is not on 'Placed' status.");
                    throw new EmptyFieldException("Order status cannot be set as 'Rejected' because it is not on 'Placed' status.");
                }
                order.setOrderStatus(OrderStatus.REJECTED);
                break;
        }


//        order.setOrderDate(orderDto.getOrderDate());
//        order.setOrderAddress(orderDto.getOrderAddress());
//        order.setDish(orderDto.getDish());
//        order.setDishName(orderDto.getDishName());
//        order.setRestaurantName(orderDto.getRestaurantName());
//        order.setRestaurantId(orderDto.getRestaurantId());
//        order.setQuantity(orderDto.getQuantity());
//        order.setTotalPrice(orderDto.getTotalPrice());
//        order.setUserId(orderDto.getUserId());
//        order.setOrderNumber(orderDto.getOrderNumber());
//        order.setOrderStatus(order.getOrderStatus());
        logger.info("Updating order.");
        return dtoConversion.convertOrder(orderRepository.save(order));
    }

    public OrderDto save(OrderDto orderDto) {

        if(orderDto.getOrderAddress()==null){
            logger.warn("Order Address cannot be null.");
            throw new EmptyFieldException("Order Address cannot be null.");
        }

        if(orderDto.getDish()==null){
            logger.warn("Order Dish cannot be null.");
            throw new EmptyFieldException("Order Dish cannot be null.");
        }

        if(orderDto.getRestaurantId()==null){
            logger.warn("Restaurant ID cannot be null.");
            throw new EmptyFieldException("Restaurant ID cannot be null.");
        }

        if(orderDto.getDishName()==null){
            logger.warn("Dish Name cannot be null.");
            throw new EmptyFieldException("Dish Name cannot be null.");
        }

        if(orderDto.getQuantity()==null){
            logger.warn("Quantity cannot be null.");
            throw new EmptyFieldException("Quantity cannot be null.");
        }

        if(orderDto.getRestaurantName()==null){
            logger.warn("Restaurant Name cannot be null.");
            throw new EmptyFieldException("Restaurant Name cannot be null.");
        }

        if(orderDto.getTotalPrice()==0){
            logger.warn("Total Price cannot be 0.");
            throw new EmptyFieldException("Total Price cannot be 0.");
        }

        if(orderDto.getUserId()==null){
            logger.warn("User ID cannot be null.");
            throw new EmptyFieldException("User ID cannot be null.");
        }



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
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order order = orderOptional.get();
        if(orderOptional.isEmpty()){
            throw new EntityNotFoundException("Order ID: "+id+ " does not exist. Try a different one.");
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String authUsername = userDetails.getUsername();
        User userAuth = userRepository.findByUsername(authUsername);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(order.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            throw new EntityNotFoundException("Restaurant ID: "+id+ " does not exist. Try a different one.");
        }
        Restaurant restaurant = restaurantOptional.get();
        if (userAuth.getRestaurantId() != restaurant.getId()){
            logger.error("You do not have access to delete this order.");
            throw new UnauthorizedException("You do not have access to delete this order.");
        }
        orderRepository.deleteById(id);
    }

    public List<OrderDto> findAll() {
        logger.info("Retrieving all orders.");
        return orderRepository.findAll().stream()
                .map(dtoConversion::convertOrder)
                .collect(Collectors.toList());
    }

    public OrderDto findById(Integer id) {
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
