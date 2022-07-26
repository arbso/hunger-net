import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {Order} from '../create-order/order';
import {OrderService} from '../create-order/order.service';
import {Restaurant} from '../models/restaurant/restaurant';

@Component({
  selector: 'app-view-orders',
  templateUrl: './view-orders.component.html',
  styleUrls: ['./view-orders.component.css']
})
export class ViewOrdersComponent implements OnInit {

  constructor(private orderService: OrderService,
              private router: Router,
              private httpClient: HttpClient) {
  }

  page = 1;
  pageSize = 10;
  orders: Order[];
  ordersjson
  restaurants: Array<Restaurant> = [];

  ngOnInit(): void {
    this.getOrders();
  }

  private getOrders() {
    this.orderService.getOrders().subscribe(data => {
      this.orders = data;

      console.log(data)

    })
  }

  private getOrdersByRestaurantId() {

  }

  public viewUser(id: number) {
    this.router.navigate(['user-details', id])
  }


}
