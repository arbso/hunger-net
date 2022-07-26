import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Menu } from '../models/menu/menu';
import { MenuService } from './menu.service';

@Component({
  selector: 'app-menu-list',
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.css']
})
export class MenuListComponent implements OnInit {


  constructor(private menuService: MenuService,
    private route: ActivatedRoute,
    private router: Router) { }

    id: number;
    menus: Menu[];
    menu: Menu;

  ngOnInit(): void {
        this.id = this.route.snapshot.params['id']
        this.menuService.getMenu(this.id).subscribe(data =>{
         this.menus = data; 
    }, error =>console.log(error));
  }

  // private getMenus(id:number){
  //   this.menuService.getMenu(id).subscribe(data =>{
  //     this.menus = data;
  //   })
  // }

  // getMenuById(id: number){
  //   this.route.navigate(['menus',id]);
  // }


  getMenuById(id: number){
    this.router.navigate(['menus',id]);
  }
}
