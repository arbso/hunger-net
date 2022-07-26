import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant } from '../models/restaurant/restaurant';
import { User } from '../models/user/user';
import { RestaurantService } from '../restaurant-list/restaurant.service';
import { UserService } from '../user-list/user.service';

@Component({
  selector: 'app-make-manager',
  templateUrl: './make-manager.component.html',
  styleUrls: ['./make-manager.component.css']
})
export class MakeManagerComponent implements OnInit {

  form: any = {};
  public user = new User();
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';
  id: number;
  restaurants: Restaurant[];
  
  constructor( 
    private router: Router, 
    private userService: UserService,
    private route: ActivatedRoute,
    private restaurantService: RestaurantService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.userService.getUser(this.id).subscribe(data =>{
      this.user = data;
    },error => console.log(error));

    this.getRestaurants();
  }

  private getRestaurants(){
    this.restaurantService.getRestaurantList().subscribe(data =>{
      this.restaurants = data;
      console.log(this.restaurants)
    })
  }

  goToPage(){
    this.router.navigate(['admin/users'])
  }

  setManagerId(id:number){
console.log(id)
this.user.restaurantId = id;
console.log(this.user)
  }

  onSubmit(){

    this.userService.makeManager(this.user.restaurantId,this.user).subscribe(
      data => {
        this.isRegister = true;
        this.isRegisterFail = false;
      },
      (err: any) => {

        this.errorMsg = err.error;
        this.isRegister = false;
        this.isRegisterFail = true;
      }
    );
  }
}
