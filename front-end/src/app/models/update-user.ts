import { UserDetails } from "./user-details";

export class UpdateUser {
    id: number;
    username: string;
    email: string;
    roles: string[];
    password: string;
    userDetails: UserDetails[];
    restaurantId: number;
  
    constructor(username: string, email: string, userDetails: UserDetails[]) {
        this.username = username;
        this.email = email;

        this.userDetails = userDetails;
      }
  }