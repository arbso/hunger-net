// import { interceptorProvider } from './services/product-interceptor.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { ViewRestaurantComponent } from './view-restaurant/view-restaurant.component';
import { MenuListComponent } from './menu-list/menu-list.component';
import { ViewMenuComponent } from './view-menu/view-menu.component';
import { DishListComponent } from './dish-list/dish-list.component';
// import { interceptorProvider, ProductInterceptorService } from './services/product-interceptor.service';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { Token } from '@angular/compiler';
import { UserListComponent } from './user-list/user-list.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { RouterModule } from '@angular/router';
import { UpdateUserComponent } from './update-user/update-user.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { ViewRestaurantsComponent } from './view-restaurants/view-restaurants.component';
import { CreateRestaurantComponent } from './create-restaurant/create-restaurant.component';
import { CartComponent } from './cart/cart.component';
import { CreateOrderComponent } from './create-order/create-order.component';
import { ViewOrdersComponent } from './view-orders/view-orders.component';
import { MakeManagerComponent } from './make-manager/make-manager.component';
import { ManageRestaurantComponent } from './manage-restaurant/manage-restaurant.component';
import { ViewResOrdersComponent } from './view-res-orders/view-res-orders.component';
import { ViewUserOrdersComponent } from './view-user-orders/view-user-orders.component';
import { CreateMenuComponent } from './create-menu/create-menu.component';
import { ManageMenusComponent } from './manage-menus/manage-menus.component';
import { InspectMenuComponent } from './inspect-menu/inspect-menu.component';
import { CreateDishComponent } from './create-dish/create-dish.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UpdateRestaurantComponent } from './update-restaurant/update-restaurant.component';
import { UpdateMenuComponent } from './update-menu/update-menu.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    UserComponent,
    AdminComponent,
    SignInComponent,
    RestaurantListComponent,
    ViewRestaurantComponent,
    MenuListComponent,
    ViewMenuComponent,
    DishListComponent,
    UserListComponent,
    CreateUserComponent,
    UpdateUserComponent,
    UserDetailsComponent,
    ViewRestaurantsComponent,
    CreateRestaurantComponent,
    CartComponent,
    CreateOrderComponent,
    ViewOrdersComponent,
    MakeManagerComponent,
    ManageRestaurantComponent,
    ViewResOrdersComponent,
    ViewUserOrdersComponent,
    CreateMenuComponent,
    ManageMenusComponent,
    InspectMenuComponent,
    CreateDishComponent,
    UpdateRestaurantComponent,
    UpdateMenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    NgbModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
  useClass: TokenInterceptorService,
multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
