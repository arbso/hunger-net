import {Component, OnInit} from '@angular/core';
import {TokenService} from 'src/app/services/token.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  info: any = {};

  constructor(private tokenService: TokenService) {
  }

  ngOnInit(): void {
    this.getInfo();
  }

  getInfo() {
    this.info = {
      token: this.tokenService.getToken(),
      username: this.tokenService.getUserName(),
      authorities: this.tokenService.getAuthorities()
    };
  }

}
