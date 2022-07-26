import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Restaurant} from '../models/restaurant/restaurant';
import {RestaurantService} from '../restaurant-list/restaurant.service';
import {TokenService} from '../services/token.service';

@Component({
  selector: 'app-manage-restaurant',
  templateUrl: './manage-restaurant.component.html',
  styleUrls: ['./manage-restaurant.component.css']
})
export class ManageRestaurantComponent implements OnInit {

  id: string;
  restaurants: Restaurant;
  info: any = {};

  constructor(private httpClient: HttpClient,
              private tokenService: TokenService,
              private restaurantService: RestaurantService,
              private route: Router) {
  }

  ngOnInit(): void {
    this.getInfo();
    this.id = this.tokenService.getUserName();
    this.restaurantService.getRestaurantByManagerId(this.id).subscribe(data => {
      this.restaurants = data;
    }, error => console.log(error));

  }

  updateRestaurant(id: number) {
    this.route.navigate(['manage-restaurant/edit-restaurant', id])
  }

  viewOrders(id: number) {
    this.route.navigate(['manage-restaurant/orders', id])
  }

  private viewUser(id: number) {
    this.route.navigate(['user-details', id])
  }

  createMenu(id: number) {
    this.route.navigate(['manage-restaurant/create-menu', id])
  }

  viewMenus(id: number) {
    this.route.navigate(['manage-restaurant/menus', id])
  }


  getInfo() {
    this.info = {
      token: this.tokenService.getToken(),
      username: this.tokenService.getUserName(),
      authorities: this.tokenService.getAuthorities()
    };
  }

}
