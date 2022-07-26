import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../models/user/user';
import {TokenService} from '../services/token.service';
import {UserService} from './user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  page = 1;
  pageSize = 5;
  users: User[];
  user: User;
  info: any = {};
  filter = new FormControl('');

  constructor(private userService: UserService,
              private router: Router,
              private route: ActivatedRoute,
              private tokenService: TokenService) {
  }

  ngOnInit(): void {
    this.getUsers();
    this.getInfo();
  }

  private getUsers() {
    this.userService.getUserList().subscribe(data => {
      this.users = data;

      console.log(this.users)
    })
  }

  sortByRole() {
    this.users.sort((a, b) => (a.roles > b.roles) ? 1 : -1)
  }

  sortByUsername() {
    this.users.sort((a, b) => (a.username > b.username) ? 1 : -1)
  }

  sortById() {
    this.users.sort((a, b) => (a.id > b.id) ? 1 : -1)
  }


  getUser(id: number) {
    this.router.navigate(['users', id]);
  }

  updateUser(id: number) {
    this.router.navigate(['update-user', id])
  }

  makeManager(id: number) {
    this.router.navigate(['admin/make-manager', id])
  }

  deleteConfirm(id: number) {

  }

  deleteUser(id: number) {
    if (confirm("Are you sure to delete this user?")) {
      this.userService.deleteUser(id).subscribe(data => {

      });
      this.router.navigate(['/admin/users'])
    } else {
      this.router.navigate(['/admin/users'])
    }
  }


  viewUserDetails(id: number) {
    this.router.navigate(['user-details', id])
  }

  getInfo() {
    this.info = {
      token: this.tokenService.getToken(),
      username: this.tokenService.getUserName(),
      authorities: this.tokenService.getAuthorities()
    };
  }

}
