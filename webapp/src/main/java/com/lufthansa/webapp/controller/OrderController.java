package com.lufthansa.webapp.controller;


import com.lufthansa.backend.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import com.lufthansa.backend.model.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.lufthansa.backend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private static final Logger logger = LogManager.getLogger(OrderController.class);
    private final OrderService orderService;


    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER','ROLE_CLIENT')")
    public ResponseEntity<OrderDto> add(@RequestBody OrderDto orderDto) {

        return ResponseEntity.ok(orderService.save(orderDto));
    }

    @PutMapping("/update/{id}/{statusNumber}")
    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<OrderDto> update(@PathVariable Integer id, @PathVariable Integer statusNumber){
        return ResponseEntity.ok(orderService.update(id,statusNumber));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        orderService.deleteById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderDto>> findAll() {
        logger.info("Finding all orders");
        List<OrderDto> orderDtos = orderService.findAll();
        if (orderDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable Integer id) {
        logger.debug(id);
        return ResponseEntity.ok(orderService.findById(id));
    }



//    @PutMapping("/approve/{id}")
//    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
//    public ResponseEntity<OrderDto> approve(@PathVariable Integer id) {
//        OrderDto approvedOrder = orderService.findById(id);
//        approvedOrder.setOrderStatus(OrderStatus.APPROVED);
//        return ResponseEntity.ok(orderService.update(approvedOrder, id));
//    }
//
//    @PutMapping("/reject/{id}")
//    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
//    public ResponseEntity<OrderDto> reject(@PathVariable Integer id) {
//        OrderDto approvedOrder = orderService.findById(id);
//        approvedOrder.setOrderStatus(OrderStatus.REJECTED);
//        return ResponseEntity.ok(orderService.update(approvedOrder, id));
//    }
//
//    @PutMapping("/prepare/{id}")
//    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
//    public ResponseEntity<OrderDto> prepare(@PathVariable Integer id) {
//        OrderDto approvedOrder = orderService.findById(id);
//        approvedOrder.setOrderStatus(OrderStatus.PREPARED);
//        return ResponseEntity.ok(orderService.update(approvedOrder, id));
//    }
//
//    @PutMapping("/wait/{id}")
//    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
//    public ResponseEntity<OrderDto> wait(@PathVariable Integer id) {
//        OrderDto approvedOrder = orderService.findById(id);
//        approvedOrder.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY);
//        return ResponseEntity.ok(orderService.update(approvedOrder, id));
//    }
//
//    @PutMapping("/deliver/{id}")
//    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
//    public ResponseEntity<OrderDto> deliver(@PathVariable Integer id) {
//        OrderDto approvedOrder = orderService.findById(id);
//        approvedOrder.setOrderStatus(OrderStatus.DELIVERED);
//        return ResponseEntity.ok(orderService.update(approvedOrder, id));
//    }

    @GetMapping("/restaurant/{id}")
    @PreAuthorize("hasRole('ROLE_RESTAURANT_MANAGER')")
    public ResponseEntity<List<OrderDto>> getOrdersByRestaurantId(@PathVariable Integer id) {
        List<OrderDto> orderDtos = orderService.getOrdersByRestaurantId(id);
        if (orderDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT_MANAGER','ROLE_CLIENT')")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Integer id) {
        List<OrderDto> orderDtos = orderService.getOrdersByUserId(id);
        if (orderDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(orderDtos);
    }

}