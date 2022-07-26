import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from '../create-order/order';
import { OrderService } from '../create-order/order.service';
import { User } from '../models/user/user';
import { TokenService } from '../services/token.service';
import { UserService } from '../user-list/user.service';

@Component({
  selector: 'app-view-user-orders',
  templateUrl: './view-user-orders.component.html',
  styleUrls: ['./view-user-orders.component.css']
})
export class ViewUserOrdersComponent implements OnInit {

  constructor(private orderService: OrderService,
    private router: Router,
    private httpClient: HttpClient,
    private route: ActivatedRoute,
    private tokenService: TokenService,
    private userService: UserService) { }

    page=1;
    pageSize=10;
    private user = new User();
    id: number;
    orders: Order[];
    private username;
    userid:any;

  ngOnInit(): void {

    this.username = this.tokenService.getUserName();
    this.userService.getUserByUsername(this.username).subscribe(data =>{
      this.user = data;
      this.userid = this.user.id
      this.orderService.getOrdersByUserId(this.user.id).subscribe(data =>{
        this.orders = data;
  
        console.log(data)
  
      })
      console.log('USER: '+this.user.username)
      console.log('USERID:' +this.user.id)
    },error => console.log(error));


  }

  private getOrders(){
    
    this.id = this.user.id
    console.log(this.userid)

  }

}
