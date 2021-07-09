import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit, Output } from '@angular/core';
import { UserService } from './services/user.service';
import { User } from './User'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public users: User[];
  public showingEditModal : boolean = false;
  public toEditUser: User;
  
  constructor(private userService: UserService) { }

  ngOnInit() {
    console.log(this.users);
    this.getUsers();
  }

  toggleEditModal(user: User) {
    this.showingEditModal = !this.showingEditModal;
    this.toEditUser = user;
  
  }

  public getUsers(): void  {
     this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}

