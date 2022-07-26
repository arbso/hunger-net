import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Restaurant} from '../models/restaurant/restaurant';
import {CreateRestaurant} from '../models/restaurant/restaurant copy';
import {RestaurantService} from '../restaurant-list/restaurant.service';

@Component({
  selector: 'app-update-restaurant',
  templateUrl: './update-restaurant.component.html',
  styleUrls: ['./update-restaurant.component.css']
})
export class UpdateRestaurantComponent implements OnInit {

  form: any = {};
  public restaurant = new Restaurant();
  updateRestaurant;
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';
  id: number;

  constructor(private restaurantService: RestaurantService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.restaurantService.getRestaurant(this.id).subscribe(data => {
      this.restaurant = data;

      console.log(this.restaurant)
    }, error => console.log(error));
  }

  onSubmit() {
    this.updateRestaurant = new CreateRestaurant(this.restaurant.restaurantName, this.restaurant.restaurantPhone, this.restaurant.restaurantEmail);
    console.log(this.updateRestaurant)
    this.restaurantService.updateRestaurant(this.id, this.updateRestaurant).subscribe(data => {
      this.isRegister = true;
      this.isRegisterFail = false;
      this.goToPage();
    }, error => console.log(error))

    this.goToPage();

  }

  goToPage() {
    this.router.navigate(['manage-restaurant'])
  }
}
