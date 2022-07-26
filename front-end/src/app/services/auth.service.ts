import { JwtModel } from './../models/jwt-model';
import { HttpBackend, HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginUser } from '../models/login-user';
import { NewUser } from '../models/new-user';
import { UpdateUser } from '../models/update-user';
import { CreateUser } from '../models/user/create-user';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl = 'http://localhost:8080';
  private httpClient: HttpClient;
  constructor(handler: HttpBackend) {this.httpClient = new HttpClient(handler); }

  public login(usuario: LoginUser): Observable<JwtModel> {
    console.log(usuario)
    return this.httpClient.post<JwtModel>(this.authUrl + '/login', usuario, cabecera);
  }

  public register(usuario: CreateUser): Observable<any> {
    return this.httpClient.post<CreateUser>(this.authUrl + '/users/add', usuario, cabecera);
  }

}
