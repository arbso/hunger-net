import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Restaurant } from '../models/restaurant/restaurant';
import { RestaurantService } from './restaurant.service';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css']
})
export class RestaurantListComponent implements OnInit {
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

  getRestaurantById(id: number){
    this.router.navigate(['restaurants',id]);
  }


}
