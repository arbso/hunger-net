import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateMenu, UpdateMenu } from '../models/menu/createmenu';
import { Menu } from '../models/menu/menu';

@Injectable({
  providedIn: 'root'
})
export class MenuService {


  private baseURLmenu = "http://localhost:8080/menus/res"
  private baseURL = "http://localhost:8080/menus";

  constructor(private httpClient: HttpClient) { }

  getMenuDishes(id:number): Observable<Menu>{
    console.log(id)
    return this.httpClient.get<Menu>(`${this.baseURL}/${id}`);
  }

  getMenu(id: number):Observable<Menu[]>{
    return this.httpClient.get<Menu[]>(`${this.baseURLmenu}/${id}`);
  }

  saveMenu(menu: CreateMenu):Observable<any>{
    return this.httpClient.post<any>(`${this.baseURL}/add`,menu);
  }

  updateMenu(id:number, menu: UpdateMenu): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/update/${id}`,menu);
  }

  getMenusByRestaurant(id: number):Observable<Menu[]>{
    return this.httpClient.get<Menu[]>(`${this.baseURLmenu}/all/${id}`);
  }

  deleteMenu(id:number): Observable<Object>{
    return this.httpClient.delete(`${this.baseURL}/delete/${id}`);
  }

}
