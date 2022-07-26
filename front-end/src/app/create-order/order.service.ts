import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from './order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseURL = "http://localhost:8080/order";


  constructor(private httpClient: HttpClient) { }

  public createOrder(order: Order):Observable<any>{
    return this.httpClient.post<any>(`${this.baseURL}/add`,order);
  }

  public getOrders():Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}`)
  }

  public getOrdersByRestaurantId(id:number):Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}/restaurant/${id}`)
  }

  public getOrdersByUserId(id:number):Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}/user/${id}`)
  }

  public approveOrder(id:number, order: Order): Observable<Object>{
    return this.httpClient.put<any>(`${this.baseURL}/approve/${id}`,order);
  }

  public prepareOrder(id:number, order: Order): Observable<Object>{
    return this.httpClient.put<any>(`${this.baseURL}/prepare/${id}`,order);
  }

  public rejectOrder(id:number, order: Order): Observable<Object>{
    return this.httpClient.put<any>(`${this.baseURL}/reject/${id}`,order);
  }

  public waitOrder(id:number, order: Order): Observable<Object>{
    return this.httpClient.put<any>(`${this.baseURL}/wait/${id}`,order);
  }

  public deliverOrder(id:number, order: Order): Observable<Object>{
    return this.httpClient.put<any>(`${this.baseURL}/deliver/${id}`,order);
  }


}
