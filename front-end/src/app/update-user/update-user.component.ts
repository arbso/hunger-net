import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NewUser } from '../models/new-user';
import { UpdateUser } from '../models/update-user';
import { UserDetails, UserDetailsC } from '../models/user-details';
import { CreateUser } from '../models/user/create-user';
import { User } from '../models/user/user';
import { AuthService } from '../services/auth.service';
import { UserService } from '../user-list/user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  form: any = {};
  public user = new User();
  updateUser;
  ud
  userDetails: UserDetails;
  userDetailsArr: Array<UserDetails>=[] ;
  isRegister = false;
  isRegisterFail = false;
  errorMsg = '';
  id: number;

  constructor(private authService: AuthService,  
    private router: Router, 
    private userService: UserService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.userService.getUser(this.id).subscribe(data =>{
      this.user = data;
      this.ud = this.user.userDetails[0]

      // this.userDetails = this.user.userDetails
      // this.userDetailsArr.push(new UserDetails(this.userDetails.firstName,this.userDetails.lastName, this.userDetails.phoneNumber))
      // this.user.userDetails = this.userDetails
      // console.log(this.user)
      console.log(this.ud)
    },error => console.log(error));
  }

  onSubmit() {
    this.userDetailsArr.push(new UserDetails(this.ud.firstName, this.ud.lastName, this.ud.phoneNumber));
    this.updateUser = new UpdateUser(this.user.username, this.user.email, this.userDetailsArr);
    console.log(this.userDetailsArr)
    this.userService.updateUser(this.id,this.updateUser).subscribe(data=>{
      this.isRegister = true;
      this.isRegisterFail = false;
      this.router.navigate(['admin/users'])
    }, error=> console.log(error))

    this.goToPage();
      
  }

  goToPage(){
    this.router.navigate(['admin/users'])
  }
}
