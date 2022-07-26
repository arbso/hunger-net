import * as internal from "assert";
import { Dish } from "../models/dish/dish";
import { Restaurant } from "../models/restaurant/restaurant";

export class Order {
    id: number;
    orderAddress: string;
    orderDate: Date;
    orderStatus: number;
    orderNumber: string;
    totalPrice: number;
    restaurantName: string;
    restaurantId: number;
    dish: Dish[];
    dishName: string;
    userId: number;
    quantity: number;
    restaurant: Restaurant;

    constructor(orderAddress: string, dish: Dish[], totalPrice: number, quantity: number,restaurantId: number, restaurantName: string, dishName: string, userId: number){
        this.orderAddress = orderAddress;
        this.dish = dish;
        this.totalPrice = totalPrice;
        this.orderStatus = 0;
        this.quantity = quantity;
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.dishName = dishName;
    }
}
