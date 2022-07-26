import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Restaurant } from '../models/restaurant/restaurant';
import { RestaurantService } from '../restaurant-list/restaurant.service';

@Component({
  selector: 'app-view-restaurants',
  templateUrl: './view-restaurants.component.html',
  styleUrls: ['./view-restaurants.component.css']
})
export class ViewRestaurantsComponent implements OnInit {

  page=1;
  pageSize=5;
  restaurants: Restaurant[];
  restaurant: Restaurant;

  constructor(private restaurantService: RestaurantService,
    private router: Router) { }

  ngOnInit(): void {
    this.getRestaurants();
  }

  private getRestaurants(){
    this.restaurantService.getRestaurantList().subscribe(data =>{
      this.restaurants = data;
    })
  }
}
