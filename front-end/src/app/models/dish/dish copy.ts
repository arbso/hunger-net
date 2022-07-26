import {Menu} from "../menu/menu";

export class CreateDish {
  id: number;
  dishName: String;
  dishDescription: String;
  dishPrice: number;
  menuId: number;

  constructor(dishName: string, dishDescription: string, dishPrice: number, menuId: number) {
    this.dishName = dishName;
    this.dishDescription = dishDescription;
    this.dishPrice = dishPrice;
    this.menuId = menuId;
  }
}
