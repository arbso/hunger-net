import { SignInComponent } from './components/sign-in/sign-in.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { GuardService } from './services/guard.service';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {GuardService as guard} from './services/guard.service';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { ViewRestaurantComponent } from './view-restaurant/view-restaurant.component';
import { ViewMenuComponent } from './view-menu/view-menu.component';
import { UserListComponent } from './user-list/user-list.component';
import { CreateUserComponent } from './create-user/create-user.component';
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
import { UpdateRestaurantComponent } from './update-restaurant/update-restaurant.component';
import { UpdateMenuComponent } from './update-menu/update-menu.component';

const routes: Routes = [
  {path: 'home', component : HomeComponent},
  {path: 'admin', component: AdminComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'user', component: UserComponent,
  canActivate: [guard], data: {expectedRol: ['user']}},
  {path: 'login', component : LoginComponent},
  {path: 'register', component : SignInComponent},
  {path: 'restaurants', component : RestaurantListComponent},
  {path: 'menus/:id', component : ViewMenuComponent},
  {path: 'restaurants/:id', component : ViewRestaurantComponent},
  {path: 'create-user', component: CreateUserComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'update-user/:id', component: UpdateUserComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'admin/users', component: UserListComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'admin/view-restaurants', component: ViewRestaurantsComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'admin/create-restaurant', component: CreateRestaurantComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'admin/view-orders', component: ViewOrdersComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'admin/make-manager/:id', component: MakeManagerComponent,
  canActivate: [guard], data: {expectedRol: ['admin']}},
  {path: 'manage-restaurant', component: ManageRestaurantComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'manage-restaurant/orders/:id', component: ViewResOrdersComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'manage-restaurant/create-menu/:id', component: CreateMenuComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'manage-restaurant/edit-restaurant/:id', component: UpdateRestaurantComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'manage-restaurant/menus/:id', component: ManageMenusComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'manage-restaurant/edit-menu/:id', component: UpdateMenuComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'manage-restaurant/inspect/:id', component: InspectMenuComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'add-dish/:id', component: CreateDishComponent,
  canActivate: [guard], data: {expectedRol: ['manager']}},
  {path: 'user-details/:id', component: UserDetailsComponent},
  {path: 'cart', component: ViewUserOrdersComponent,
  canActivate: [guard], data: {expectedRol: ['user','admin','manager']}},
  {path: 'create-order/:id', component: CreateOrderComponent},
  {path: '', component : HomeComponent},
  {path: '**', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
