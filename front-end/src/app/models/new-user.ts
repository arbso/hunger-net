import { UserDetails } from "./user-details";

export class NewUser {
  username: string;
  email: string;
  roles: string[];
  password: string;
  userDetails: UserDetails;

  constructor(username: string, email: string, password: string) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = ['ROLE_CLIENT'];
  }

  setPhoneNumber(userDetails: UserDetails) {
    this.userDetails = userDetails;
}

}
