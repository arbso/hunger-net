import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Order} from '../create-order/order';
import {OrderService} from '../create-order/order.service';
import {Restaurant} from '../models/restaurant/restaurant';
import {User} from '../models/user/user';
import {TokenService} from '../services/token.service';
import {UserService} from '../user-list/user.service';

@Component({
  selector: 'app-view-res-orders',
  templateUrl: './view-res-orders.component.html',
  styleUrls: ['./view-res-orders.component.css']
})
export class ViewResOrdersComponent implements OnInit {

  constructor(private orderService: OrderService,
              private router: Router,
              private httpClient: HttpClient,
              private route: ActivatedRoute,
              private tokenService: TokenService,
              private userService: UserService) {
  }

  page = 1;
  pageSize = 10;
  id: number;
  orders: Order[];
  ordersjson
  restaurants: Array<Restaurant> = [];
  usernames: any[];
  private user = new User();

  ngOnInit(): void {
    this.getOrders();

  }

  sortByStatus() {
    this.orders.sort((a, b) => (a.orderStatus > b.orderStatus) ? 1 : -1)
  }

  public viewUser(id: number) {
    this.router.navigate(['user-details', id])
  }

  private getOrders() {
    this.id = this.route.snapshot.params['id']
    this.orderService.getOrdersByRestaurantId(this.id).subscribe(data => {
      this.orders = data;
      console.log(data)

    })
  }

  public approveOrder(id: number, order: Order) {

    this.orderService.approveOrder(id, order).subscribe(data => {
      window.location.reload();
    }, error => console.log(error));

  }

  public rejectOrder(id: number, order: Order) {

    this.orderService.rejectOrder(id, order).subscribe(data => {
      window.location.reload();
    }, error => console.log(error));

  }

  public prepareOrder(id: number, order: Order) {

    this.orderService.prepareOrder(id, order).subscribe(data => {
      window.location.reload();
    }, error => console.log(error));

  }

  public waitOrder(id: number, order: Order) {

    this.orderService.waitOrder(id, order).subscribe(data => {
      window.location.reload();
    }, error => console.log(error));

  }

  public deliverOrder(id: number, order: Order) {

    this.orderService.deliverOrder(id, order).subscribe(data => {
      window.location.reload();
    }, error => console.log(error));

  }

  private getOrdersByRestaurantId() {

  }


}

