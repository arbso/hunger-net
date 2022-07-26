import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {Dish} from '../models/dish/dish';
import {CreateDish} from '../models/dish/dish copy';

@Injectable({
  providedIn: 'root'
})
export class DishService {

  private baseURLmenu = "http://localhost:8080/dishes/menu"
  private baseURL = "http://localhost:8080/menus";
  private dishURL = "http://localhost:8080/dishes";

  constructor(private httpClient: HttpClient, private router: Router) {
  }

  saveDish(dish: CreateDish): Observable<Dish[]> {
    return this.httpClient.post<any>(`${this.dishURL}/add`, dish);
  }

  getDishesList(id: number): Observable<Dish[]> {
    return this.httpClient.get<Dish[]>(`${this.baseURLmenu}/${id}`);
  }

  getDish(id: number): Observable<Dish> {
    return this.httpClient.get<Dish>(`${this.dishURL}/${id}`);
  }

}
