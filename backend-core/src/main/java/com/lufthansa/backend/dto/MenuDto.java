package com.lufthansa.backend.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class MenuDto {

    private Integer id;

    private String menuName;

    private String menuDescription;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")

    private Date menuOpeningTime;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date menuClosingTime;

    private Integer restaurantId;

    private String restaurantName;

    private Set<DishDto> dishes = new HashSet<>();

}
