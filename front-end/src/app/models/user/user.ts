import { UserDetails, UserDetailsC } from "../user-details";

export class User {
    id: number;
    username: string;
    email: string;
    roles: string[];
    password: string;
    restaurantId: number;
    userDetails: UserDetails[];


    // constructor(username: string, id: number){
    //     this.username = username;
    //     this.id = id;
    // }

    // constructor(username: string, email: string, password: string) {
    //     this.username = username;
    //     this.email = email;
    //     this.password = password;
    //     this.roles = ['ROLE_CLIENT'];
    //   }
}
