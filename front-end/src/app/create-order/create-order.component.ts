import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DishService } from '../dish-list/dish.service';
import { MenuService } from '../menu-list/menu.service';
import { Dish } from '../models/dish/dish';
import { Menu } from '../models/menu/menu';
import { Restaurant } from '../models/restaurant/restaurant';
import { User } from '../models/user/user';
import { RestaurantService } from '../restaurant-list/restaurant.service';
import { TokenService } from '../services/token.service';
import { UserService } from '../user-list/user.service';
import { Order } from './order';
import { OrderService } from './order.service';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {

  constructor(private router: Router, 
    private orderService: OrderService, private route: ActivatedRoute,public dishService: DishService,
    private userService: UserService,
    private tokenService: TokenService,
    private menuService: MenuService,
    private restaurantService: RestaurantService) { }

  form: any = {};
  private username;
  private order;
  public dish: Dish;
  private menu;
  private price;
  private dishPrice;
  private user = new User();
  private menuId: number;
  private restaurantId: number;
  private restaurantName: string;
  dishArr: Array<Dish> = [];
  newDish;
  // userDetails: Array<UserDetails> = [];
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';
  id:number;
  quantity: number;

  ngOnInit(): void {
    this.username = this.tokenService.getUserName();
    this.userService.getUserByUsername(this.username).subscribe(data =>{
      this.user = data;
      console.log('USER: '+this.user.username)
      console.log('USERID:' +this.user.id)
    },error => console.log(error));

    this.id = this.route.snapshot.params['id']
    this.dishService.getDish(this.id).subscribe(data =>{
      this.dish = data;
      
      console.log(this.dish)
      this.menu = this.dish.menuId
      console.log(this.menu)

      this.menuService.getMenuDishes(this.menu).subscribe(data =>{
          this.menu = data;
          console.log(this.menu)
          console.log(this.menu.restaurantId)
          console.log(this.menu.restaurantName)
      })

      this.restaurantId = this.menu.restaurantId;
      console.log('RESTAURANT ID'+this.menu.restaurantId)
    },error => console.log(error));

  }

  qtyChange(value: number){
    this.quantity = value;
    this.dish.dishPrice = this.dish.dishPrice * value;
  }

  onSubmit(){

    this.newDish = new Dish(this.dish.id,this.dish.dishName,this.dish.dishDescription, this.dish.dishPrice);
    this.price = this.dish.dishPrice;
    this.order = new Order(this.form.orderAddress, this.newDish, this.price, this.quantity, this.menu.restaurantId, this.menu.restaurantName, this.dish.dishName, this.user.id);
    console.log(this.order)
    this.orderService.createOrder(this.order).subscribe(
      data => {
        this.isRegister = true;
        this.isRegisterFail = false;
        this.goToPage();
      },
      (err: any) => {
        console.log(err.error.mensaje);
        this.errorMsg = err.error.mensaje;
        this.isRegister = false;
        this.isRegisterFail = true;
        
      }
      
    );
    this.goToPage();
  }

  goToPage(){
    this.router.navigate(['menus',this.menu])
  }

}
