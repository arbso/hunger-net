import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MenuService} from '../menu-list/menu.service';
import {Dish} from '../models/dish/dish';
import {CreateMenu} from '../models/menu/createmenu';
import {RestaurantService} from '../restaurant-list/restaurant.service';

@Component({
  selector: 'app-create-menu',
  templateUrl: './create-menu.component.html',
  styleUrls: ['./create-menu.component.css']
})
export class CreateMenuComponent implements OnInit {

  form: any = {};
  private menu: CreateMenu;
  private restaurant
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';
  private restaurantId: number;
  dishArr: Array<Dish> = [];

  constructor(private menuService: MenuService, private restaurantService: RestaurantService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.restaurantId = this.route.snapshot.params['id']
    this.restaurantService.getRestaurant(this.restaurantId).subscribe(data => {
      this.restaurant = data;
      this.menu = new CreateMenu(this.form.menuName, this.form.menuDescription, this.form.menuOpeningTime, this.form.menuClosingTime, this.restaurantId, this.restaurant.restaurantName);
      this.menu.dishes = this.dishArr;
      this.menuService.saveMenu(this.menu).subscribe(
        data => {
          this.isRegister = true;
          this.isRegisterFail = false;
        },
        (err: any) => {
          this.isRegister = false;
          this.isRegisterFail = true;
          this.errorMsg = err.error.message;
        }
      );
    })

  }
}
