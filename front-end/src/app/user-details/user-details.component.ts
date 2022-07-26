import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserDetails} from '../models/user-details';
import {User} from '../models/user/user';
import {AuthService} from '../services/auth.service';
import {UserService} from '../user-list/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  id: number;
  user: User;
  ud: UserDetails[];

  constructor(private authService: AuthService,
              private router: Router,
              private userService: UserService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']

    this.userService.getUser(this.id).subscribe(data => {
      this.user = data
    })
  }

}
