import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MenuService} from '../menu-list/menu.service';
import {CreateMenu, UpdateMenu} from '../models/menu/createmenu';
import {Menu} from '../models/menu/menu';

@Component({
  selector: 'app-update-menu',
  templateUrl: './update-menu.component.html',
  styleUrls: ['./update-menu.component.css']
})
export class UpdateMenuComponent implements OnInit {

  form: any = {};
  public menu = new Menu();
  updateMenu;
  ud
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';
  id: number;

  constructor(private menuService: MenuService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.menuService.getMenuDishes(this.id).subscribe(data => {
      this.menu = data;

    }, error => console.log(error));
  }

  onSubmit() {
    this.updateMenu = new UpdateMenu(this.menu.menuName, this.menu.menuDescription, this.menu.menuOpeningTime, this.menu.menuClosingTime, this.menu.restaurantId, this.menu.restaurantName);
    this.menuService.updateMenu(this.id, this.updateMenu).subscribe(data => {
      this.isRegister = true;
      this.isRegisterFail = false;
      this.goToPage();
    }, error => console.log(error))

    this.goToPage();

  }

  goToPage() {
    this.router.navigate(['manage-restaurant/menus', this.menu.restaurantId])
  }


}
