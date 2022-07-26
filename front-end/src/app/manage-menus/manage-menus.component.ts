import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Route, Router} from '@angular/router';
import {MenuService} from '../menu-list/menu.service';
import {Menu} from '../models/menu/menu';
import {TokenService} from '../services/token.service';

@Component({
  selector: 'app-manage-menus',
  templateUrl: './manage-menus.component.html',
  styleUrls: ['./manage-menus.component.css']
})
export class ManageMenusComponent implements OnInit {

  page = 1;
  pageSize = 5;
  id: number;
  menus: Menu[];
  info: any = {};

  constructor(private httpClient: HttpClient,
              private tokenService: TokenService,
              private menuService: MenuService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {

    this.id = this.route.snapshot.params['id'];
    this.menuService.getMenusByRestaurant(this.id).subscribe(data => {
      this.menus = data;
    }, error => console.log(error));

  }

  deleteMenu(id: number) {
    if (confirm("Are you sure to delete this menu?")) {
      this.menuService.deleteMenu(id).subscribe(data => {

      });
      window.location.reload();
    }
  }

  inspectMenu(id: number) {
    this.router.navigate(['manage-restaurant/inspect/', id])
  }

  editMenu(id: number) {
    this.router.navigate(['manage-restaurant/edit-menu/', id])
  }

}
