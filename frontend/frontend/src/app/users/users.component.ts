import { Component, OnInit } from '@angular/core';
import { User } from '../User'
import { UserService } from '../services/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: User[]
  showingEditModal : boolean = false
  showingDeleteModal: boolean = false
  showingAddModal: boolean = false
  toEditUser: User
  toDeleteUser: User
  userBody: User
  
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.getUsers();
  }

  toggleEditModal(user: User) {
    this.showingEditModal = !this.showingEditModal
    this.toEditUser = user
  }

  toggleDeleteModal(user: User) {
    this.showingDeleteModal = !this.showingDeleteModal
    this.toDeleteUser = user
  }

  toggleAddModal() {
    this.showingAddModal = !this.showingAddModal
    console.log('add modal', this.showingAddModal)
  }

  closeEditModal() {
    this.showingEditModal = false
  }

  closeDeleteModal() {
    this.showingDeleteModal = false
  }

  performPostAddRequest(user: User) {
    user.roles = this.convertToArrayRoles(user);
    this.addUser(user);
    this.showingAddModal = false
  }

  public addUser(user: User) {
    this.userService.createUser(user).subscribe(
      (response: User) => {
        console.log('created', user)
      },
      (error : HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public deleteUser(user: User) : void {
    console.log(user)
    this.userService.deleteUser(user.id).subscribe(
      () => {
        console.log('success when deleting')
        this.users = this.users.filter((u) => {
          return u.id !== user.id
        })
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      })
  }

  performDeleteRequest(user: User) {
    this.showingDeleteModal = false
    this.deleteUser(user)
  }

  performEditPutRequest(receivedUserBody: User) {
    this.showingEditModal = false
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

