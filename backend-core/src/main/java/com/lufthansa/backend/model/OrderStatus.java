package com.lufthansa.backend.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum OrderStatus {

    PLACED (0, "Created" ),
    APPROVED (1, "Approved"),
    PREPARED (2, "Prepared"),
    WAITING_FOR_DELIVERY (3, "Waiting for delivery"),
    DELIVERED (4, "Delivered"),
    REJECTED (5, "Rejected");

    int code;
    String description;

    private OrderStatus (int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatus getByCode(Integer code) {

        Stream<OrderStatus> values = Arrays.stream(OrderStatus.values());

        Predicate<OrderStatus> predicate = x -> x.getCode() == code;

        Optional<OrderStatus> result = values.filter(predicate).findFirst();

        return result.get();

    }

}