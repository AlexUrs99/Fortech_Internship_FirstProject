import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/User';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  myUser: User = {
    id: -1,
    username: "foo",
    fullName: "bar",
    email: "foo@example.com",
    password: "foo123",
    traits: ['FOCUSED'],
    gender: 'APACHE_HELICOPTER'
  }
  myUserId: Number

  constructor(private route: ActivatedRoute, private userService:UserService) { }

  ngOnInit(): void {

    this.route.paramMap
      .subscribe(params => {
        this.myUserId = Number(params.get('id'))

        this.userService.getUserAtId(this.myUserId.valueOf()).subscribe((u) => {
          this.myUser = u;
          console.log(u)
        },
        (error : HttpErrorResponse) => 
          alert(error.message)
        )
      })




  }

}
