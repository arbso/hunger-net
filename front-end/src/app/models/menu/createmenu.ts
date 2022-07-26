import { Time } from "@angular/common";
import { Observable } from "rxjs";
import { Dish } from "../dish/dish";
import { Restaurant } from "../restaurant/restaurant";

export class CreateMenu {

    id: number;
    menuName: string;
    menuDescription: string;
    menuOpeningTime: Time;
    menuClosingTime: Time;
    dishes: Dish[]; 
    restaurantId:number;
    restaurant: Observable<Restaurant>;
    restaurantName: string;

    constructor(menuName: string, menuDescription: string, menuOpeningTime: Time, menuClosingTime: Time, restaurantId: number, restaurantName: string){
        this.menuName=menuName;
        this.menuDescription = menuDescription;
        this.menuOpeningTime=menuOpeningTime;
        this.menuClosingTime = menuClosingTime;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}

export class UpdateMenu {

    id: number;
    menuName: string;
    menuDescription: string;
    menuOpeningTime: Time;
    menuClosingTime: Time;
    dishes: Dish[]; 
    restaurantId:number;
    restaurant: Observable<Restaurant>;
    restaurantName: string;

    constructor(menuName: string, menuDescription: string, menuOpeningTime: Time, menuClosingTime: Time, restaurantId: number, restaurantName: string){
        this.menuName=menuName;
        this.menuDescription = menuDescription;
        this.menuOpeningTime=menuOpeningTime;
        this.menuClosingTime = menuClosingTime;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}