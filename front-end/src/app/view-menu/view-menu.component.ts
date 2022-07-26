import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MenuService} from '../menu-list/menu.service';
import {Menu} from '../models/menu/menu';

@Component({
  selector: 'app-view-menu',
  templateUrl: './view-menu.component.html',
  styleUrls: ['./view-menu.component.css']
})
export class ViewMenuComponent implements OnInit {

  id: number;
  menu: Menu = new Menu();

  constructor(private menuService: MenuService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.menuService.getMenuDishes(this.id).subscribe(data => {
      this.menu = data;
    }, error => console.log(error));

  }

}
