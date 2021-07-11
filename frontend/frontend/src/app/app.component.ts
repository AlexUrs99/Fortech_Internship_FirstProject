import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UserService } from './services/user.service';
import { User } from './User'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  users: User[]
  showingEditModal : boolean = false
  showingDeleteModal: boolean = false
  toEditUser: User
  private userBody: User
  private userIdForDelete: number
  
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.getUsers();
  }

  toggleEditModal(user: User) {
    this.showingEditModal = !this.showingEditModal
    this.toEditUser = user
  }

  closeEditModal() {
    console.log('I arrived here')
    this.showingEditModal = false
  }

  toggleDeleteModal() {
    this.showingDeleteModal = !this.showingDeleteModal
    console.log(this.showingDeleteModal)
  }

  performEditPutRequest(receivedUserBody: User) {

    console.log(receivedUserBody)
    receivedUserBody.roles = this.convertToArrayRoles(receivedUserBody);
    this.editUser(receivedUserBody, this.toEditUser.id);

  }

  private convertToArrayRoles(receivedUserBody : any): String[] {
    let array: String[] = []
    Object.keys(receivedUserBody.roles).forEach(element => {
      if(receivedUserBody.roles[element] === true)
      array.push(element.toUpperCase())
    });
    console.log(array)
    return array
  }


  public editUser(userBody: User, userId: number) {
    console.log(userBody, userId)
    this.userService.editUser(userBody, userId).subscribe(
      (response: User) => {
        console.log('success');
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }


  public getUsers(): void  {
     this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.users = response
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}

