import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {UpdateUser} from '../models/update-user';
import {CreateUser} from '../models/user/create-user';
import {User} from '../models/user/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = "http://localhost:8080/users";

  constructor(private httpClient: HttpClient) {
  }

  getUserList(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseURL}`);
  }

  getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.baseURL}/${id}`);
  }

  getUserByUsername(username: string): Observable<User> {
    return this.httpClient.get<User>(`${this.baseURL}/username/${username}`);
  }

  updateUser(id: number, user: UpdateUser): Observable<Object> {
    console.log(user)
    return this.httpClient.patch(`${this.baseURL}/update/${id}`, user);
  }

  makeManager(id: number, user: User): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/make-manager/${id}`, user);
  }

  deleteUser(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/delete/${id}`, {});
  }


}
