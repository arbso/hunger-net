import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Restaurant} from '../models/restaurant/restaurant';
import {CreateRestaurant} from '../models/restaurant/restaurant copy';

// import { interceptorProvider, ProductInterceptorService } from '../services/product-interceptor.service';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  private baseURL = "http://localhost:8080/restaurants";

  constructor(private httpClient: HttpClient) {
  }

  getRestaurantList(): Observable<Restaurant[]> {
    return this.httpClient.get<Restaurant[]>(`${this.baseURL}`);
  }

  getRestaurant(id: number): Observable<Restaurant> {
    return this.httpClient.get<Restaurant>(`${this.baseURL}/${id}`);
  }

  updateRestaurant(id: number, restaurant: CreateRestaurant): Observable<Object> {
    return this.httpClient.patch(`${this.baseURL}/update/${id}`, restaurant);
  }

  saveRestaurant(restaurant: CreateRestaurant): Observable<any> {
    return this.httpClient.post<any>(`${this.baseURL}/add`, restaurant);
  }

  getRestaurantByManagerId(id: string): Observable<Restaurant> {
    return this.httpClient.get<any>(`${this.baseURL}/user/${id}`);
  }
}
