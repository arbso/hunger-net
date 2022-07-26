package com.lufthansa.backend.service;

import com.lufthansa.backend.exception.ResourceNotFoundException;
import com.lufthansa.backend.model.Order;
import com.lufthansa.backend.model.OrderStatus;
import com.lufthansa.backend.repository.OrderRepository;
import com.lufthansa.backend.converter.DtoConversion;
import com.lufthansa.backend.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private final DtoConversion dtoConversion;
    private final OrderRepository orderRepository;

    public OrderDto update(OrderDto orderDto, Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
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
        return orderRepository.getOrdersByRestaurantId(id).stream()
                .map(dtoConversion::convertOrder)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByUserId(Integer id) {
        logger.info("Finding orders by user");
        return orderRepository.getOrdersByUserId(id).stream()
                .map(dtoConversion::convertOrder)
                .collect(Collectors.toList());
    }


}
