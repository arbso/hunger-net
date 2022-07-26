import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Restaurant} from '../models/restaurant/restaurant';
import {RestaurantService} from '../restaurant-list/restaurant.service';

@Component({
  selector: 'app-view-restaurant',
  templateUrl: './view-restaurant.component.html',
  styleUrls: ['./view-restaurant.component.css']
})
export class ViewRestaurantComponent implements OnInit {

  id: number;
  restaurant: Restaurant = new Restaurant();

  constructor(private restaurantService: RestaurantService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.restaurantService.getRestaurant(this.id).subscribe(data => {
      this.restaurant = data;
    }, error => console.log(error));

  }

}
