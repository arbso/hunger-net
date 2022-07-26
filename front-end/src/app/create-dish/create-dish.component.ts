import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DishService} from '../dish-list/dish.service';
import {CreateDish} from '../models/dish/dish copy';

@Component({
  selector: 'app-create-dish',
  templateUrl: './create-dish.component.html',
  styleUrls: ['./create-dish.component.css']
})
export class CreateDishComponent implements OnInit {

  form: any = {};
  private dish: CreateDish;
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';
  private menuId: number;

  constructor(private dishService: DishService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.menuId = this.route.snapshot.params['id']
    this.dish = new CreateDish(this.form.dishName, this.form.dishDescription, this.form.dishPrice, this.menuId);
    this.dishService.saveDish(this.dish).subscribe(
      data => {
        this.isRegister = true;
        this.isRegisterFail = false;
        this.router.navigate(['manage-restaurant/inspect', this.menuId])
      },
      (err: any) => {
        console.log(err.error.message);
      }
    );
  }
}
