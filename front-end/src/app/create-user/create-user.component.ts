import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NewUser} from '../models/new-user';
import {UserDetails} from '../models/user-details';
import {User} from '../models/user/user';
import {AuthService} from '../services/auth.service';
import {CreateUser} from '../models/user/create-user';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  form: any = {};
  private user;
  userDetails: Array<UserDetails> = [];
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.userDetails.push(new UserDetails(this.form.firstName, this.form.lastName, this.form.phoneNumber))
    this.user = new CreateUser(this.form.username, this.form.email, this.form.password, this.userDetails);

    this.authService.register(this.user).subscribe(
      data => {
        this.isRegister = true;
        this.isRegisterFail = false;
        this.router.navigate(['admin']);
      },
      (err: any) => {
        this.errorMsg = err.error.message;
        this.isRegister = false;
        this.isRegisterFail = true;
      }
    );

  }


}
