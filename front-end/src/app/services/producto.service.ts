import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// import { Producto } from '../models/update-user';

const cabecera = { headers: new HttpHeaders({ 'Content-TYpe': 'application/json' }) };
console.log(cabecera);


@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  // productoUrl = 'http://localhost:8085/api/productos';

  // constructor(private httpClient: HttpClient) { }

  // public getList(): Observable<Producto[]> {
  //   return this.httpClient.get<Producto[]>(this.productoUrl + '/lista', cabecera);
  // }

  // public getDetail(id: number): Observable<Producto> {
  //   return this.httpClient.get<Producto>(this.productoUrl + `/detalle/${id}`, cabecera);
  // }

  // public createProduct(producto: Producto): Observable<any> {
  //   return this.httpClient.post<any>(this.productoUrl + '/nuevo', producto, cabecera);
  // }

  // public editProduct(producto: Producto, id: number): Observable<any> {
  //   return this.httpClient.put<any>(this.productoUrl + `/actualizar/${id}`, producto, cabecera);
  // }

  // public deleteProduct(id: number): Observable<any> {
  //   return this.httpClient.delete<any>(this.productoUrl + `/borrar/${id}`, cabecera);
  // }

}
