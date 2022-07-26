import {Router} from '@angular/router';
import {TokenService} from './services/token.service';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  isLogin = false;
  roles: string[];
  authority: string;
  title: any;
  username: any;
  info: any = {};

  constructor(private tokenService: TokenService,
              private router: Router) {
  }

  ngOnInit() {
    this.getToken();
    this.getInfo();
  }

  getToken() {
    if (this.tokenService.getToken()) {
      this.isLogin = true;
      this.roles = [];
      this.roles = this.tokenService.getAuthorities();
      this.roles.every(rol => {
        console.log(rol)
        if (rol === 'ROLE_ADMIN') {
          this.authority = 'admin';
          console.log(this.authority)
          return false;
        }
        if (rol === 'ROLE_RESTAURANT_MANAGER') {
          this.authority = 'manager';
          console.log(this.authority)
          return false;
        }
        this.authority = 'user';
        return true;
      });

    }

  }

  logOut(): void {
    this.tokenService.logOut();
    this.isLogin = false;
    this.authority = '';
    this.router.navigate(['home']);
  }

  restaurants(): void {
    this.router.navigate(['restaurants']);
  }

  public getUserName(): string {
    return sessionStorage.getItem("AuthUserName");
  }


  getInfo() {
    this.info = {
      token: this.tokenService.getToken(),
      username: this.tokenService.getUserName(),
      authorities: this.tokenService.getAuthorities()
    };
  }

}
