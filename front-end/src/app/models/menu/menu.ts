import { Time } from "@angular/common";
import { Dish } from "../dish/dish";
import { Restaurant } from "../restaurant/restaurant";

export class Menu {
    id: number;
    menuName: string;
    menuDescription: string;
    menuOpeningTime: Time;
    menuClosingTime: Time;
    dishes: Dish[]; 
    restaurantId:number;
    restaurant: Restaurant;
    restaurantName: string;


}
