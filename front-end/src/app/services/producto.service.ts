import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// import { Producto } from '../models/update-user';

const cabecera = { headers: new HttpHeaders({ 'Content-TYpe': 'application/json' }) };
console.log(cabecera);


@Injectable({
  providedIn: 'root'
})
export class ProductService {


}
