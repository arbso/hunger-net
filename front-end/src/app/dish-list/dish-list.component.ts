import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Dish} from '../models/dish/dish';
import {DishService} from './dish.service';

@Component({
  selector: 'app-dish-list',
  templateUrl: './dish-list.component.html',
  styleUrls: ['./dish-list.component.css']
})
export class DishListComponent implements OnInit {

  constructor(private dishService: DishService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  id: number;
  dishes: Dish[];
  dish: Dish;

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.dishService.getDishesList(this.id).subscribe(data => {
      this.dishes = data;
    }, error => console.log(error));
  }

  createOrder(id: number) {
    this.router.navigate(['create-order', id])
  }

}
