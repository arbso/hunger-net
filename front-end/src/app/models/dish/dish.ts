import {Menu} from "../menu/menu";

export class Dish {
  id: number;
  dishName: string;
  dishDescription: string;
  dishPrice: number;
  dishMenu: Menu;
  menuId: number;

  constructor(id: number, dishName: string, dishDescription: string, dishPrice: number) {
    this.id = id;
    this.dishName = dishName;
    this.dishDescription = dishDescription;
    this.dishPrice = dishPrice;
  }
}
