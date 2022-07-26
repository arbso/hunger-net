import { NewUser } from './../../models/new-user';
import { AuthService } from 'src/app/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { UserDetails } from 'src/app/models/user-details';
import { CreateUser } from 'src/app/models/user/create-user';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  form: any = {};
  private user;
  userDetails: Array<UserDetails> = [];
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onRegister() {
    this.userDetails.push(new UserDetails(this.form.firstName, this.form.lastName, this.form.phoneNumber))
    this.user = new CreateUser(this.form.username, this.form.email, this.form.password, this.userDetails);
    this.authService.register(this.user).subscribe(
      data => {
        this.isRegister = true;
        this.isRegisterFail = false;
      },
      (err: any) => {
        console.log(err.error.mensaje);
        this.errorMsg = err.error.mensaje;
        this.isRegister = false;
        this.isRegisterFail = true;
      }
    );
  }

}
