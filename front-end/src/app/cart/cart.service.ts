import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

//   public cartServiceEvent = new BehaviorSubject({});
//   cartQty = 0;
//   cartObj = [];
//  public cartTotalPrice :any;
//  private baseURL = "http://localhost:8080/";
//   constructor(private http:HttpClient) {

//    this.getCartDetailsByUser(); 
//    }

//    getCartDetailsByUser(){
//      this.http.post("addtocart/getCartsByUserId",{}).subscribe((data:any)=>{
//       //alert("Error while fetching the cart Details");
//       this.cartObj = data;
//       this.cartQty = data.length;
//      this.cartTotalPrice = this.getTotalAmounOfTheCart();
//       this.cartServiceEvent.next({"status":"completed"})//emitter
//      },error=>{
//        alert("Error while fetching the cart Details");
//      })
//    }


//   addCart(obj){
//     //this.cartServiceEvent.next({"status":"completed"})//emitter
//     var request  = {
//       "productId":obj.productId,
//       "qty":obj.qty,
//       "price":obj.price
//     }
//     this.http.post("addtocart/addProduct",request).subscribe((data:any)=>{
//       this.getCartDetailsByUser()
//     },
//     error=>{
//       //if the products is already in cart
//         alert("Error in AddCart API "+error.message);
//     })
//   }
//   getCartOBj(){
//     return this.cartObj;
//   }
//   getTotalAmounOfTheCart(){
//     let obj = this.cartObj;
//     let totalPrice  = 0;
   
//     for(var o in obj ){      
//       totalPrice = totalPrice +parseFloat(obj[o].price);
//     }

//     return totalPrice.toFixed(2);
//   }
//   getQty(){
//     return this.cartQty;
//   }


//   removeCart(cartId){
//       var request = {
//           "productId":"dummy_val",
//           "cartId":cartId,
//       }
//       this.http.post("addtocart/removeProductFromCart",request).subscribe((data:any)=>{
//           this.getCartDetailsByUser();
//       },error=>{
//         alert("Error while fetching the cart Details");
//       })
//   }
}
