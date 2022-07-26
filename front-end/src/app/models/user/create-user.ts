import { UserDetails } from '../user-details';
export class CreateUser {
    id: number;
    username: string;
    email: string;
    roles: string[];
    password: string;
    userDetails: UserDetails[];
    restaurantId: number;

    constructor(username: string, email: string, password: string, userDetails: UserDetails[]) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = ['ROLE_CLIENT'];
        this.userDetails = userDetails;
      }
}


