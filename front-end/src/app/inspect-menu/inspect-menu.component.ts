import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DishService } from '../dish-list/dish.service';
import { MenuService } from '../menu-list/menu.service';
import { Dish } from '../models/dish/dish';
import { Menu } from '../models/menu/menu';
import { TokenService } from '../services/token.service';

@Component({
  selector: 'app-inspect-menu',
  templateUrl: './inspect-menu.component.html',
  styleUrls: ['./inspect-menu.component.css']
})
export class InspectMenuComponent implements OnInit {
  page=1;
  pageSize=10;
  id: number;
  menu: Menu;
  info: any = {};
  dishes: Dish[];
  dish: Dish;
  dishesTest: Dish[];

  constructor(private httpClient:HttpClient,
    private tokenService: TokenService,
    private menuService: MenuService,
    private route: ActivatedRoute,
    private router: Router,
    private dishService: DishService) { }

    ngOnInit(): void {

      this.id = this.route.snapshot.params['id'];
      this.menuService.getMenuDishes(this.id).subscribe(data =>{
        this.menu = data; 
        this.dishesTest = this.menu.dishes
        console.log(this.menu)
      }, error =>console.log(error));


          this.id = this.route.snapshot.params['id']
          this.dishService.getDishesList(this.id).subscribe(data =>{
           this.dishes = data;
           console.log('bruuh'+this.dishes) 
          })
    }

    addDish(id: number){
      this.router.navigate(['add-dish',id])
    }

    editDish(id: number){
      
    }

    deleteDish(id:number){

    }
}
