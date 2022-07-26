import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user-list/user.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  public user = 'admin';

  constructor(private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
  }

  getUsers(){
    this.router.navigate(['admin/users']);
  }
}
