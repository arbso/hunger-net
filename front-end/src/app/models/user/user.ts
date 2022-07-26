import {UserDetails, UserDetailsC} from "../user-details";

export class User {
  id: number;
  username: string;
  email: string;
  roles: string[];
  password: string;
  restaurantId: number;
  userDetails: UserDetails[];


}
