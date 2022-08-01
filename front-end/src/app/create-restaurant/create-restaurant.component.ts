import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Restaurant} from '../models/restaurant/restaurant';
import {CreateRestaurant} from '../models/restaurant/restaurant copy';
import {RestaurantService} from '../restaurant-list/restaurant.service';

@Component({
  selector: 'app-create-restaurant',
  templateUrl: './create-restaurant.component.html',
  styleUrls: ['./create-restaurant.component.css']
})
export class CreateRestaurantComponent implements OnInit {

  form: any = {};
  private restaurant: CreateRestaurant;
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';

  constructor(private restaurantService: RestaurantService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.restaurant = new CreateRestaurant(this.form.restaurantName, this.form.restaurantPhone, this.form.restaurantEmail);
    this.restaurantService.saveRestaurant(this.restaurant).subscribe(
      data => {
        this.isRegister = true;
        this.isRegisterFail = false;
        this.router.navigate['/admin/']
      },
      (err: any) => {
        this.isRegister = false;
        this.isRegisterFail = true;
        this.errorMsg = err.error.message;
      }
    );
  }

}
