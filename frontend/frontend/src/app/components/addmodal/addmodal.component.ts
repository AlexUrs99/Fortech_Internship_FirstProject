import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/User';

@Component({
  selector: 'app-addmodal',
  templateUrl: './addmodal.component.html',
  styleUrls: ['./addmodal.component.css']
})
export class AddModalComponent implements OnInit {
  addForm: FormGroup;
  @Output() onCloseAdd = new EventEmitter()
  @Output() onAddFormSubmitted = new EventEmitter()

  constructor(private fb: FormBuilder, public router: Router, private userService: UserService) { }

  ngOnInit(): void {
    this.addForm = this.fb.group({
      username: new FormControl(null, [
        Validators.required
      ]),
      fullName: new FormControl(null),
      email: new FormControl(null, [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.pattern('^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$')
      ]),
      traits: this.fb.group({
        courageous: new FormControl(false),
        caring: new FormControl(false),
        focused: new FormControl(false),
        perfectionist: new FormControl(false),
      }),
      gender: new FormControl(false)
    })
  }

  get email() {
    return this.addForm.get('email')
  }

  get password() {
    return this.addForm.get('password')
  }

  get username() {
    return this.addForm.get('username')
  }

  get fullName() {
    return this.addForm.get('fullName')
  }

  get traits() {
    return this.addForm.get('traits')
  }

  get gender() {
    return this.addForm.get('gender')
  }

  onSubmit() {
    const userBody: User = this.addForm.value
    userBody.traits = this.convertToArrayRoles(userBody)
    
    this.userService.createUser(userBody).subscribe(
      (response: User) => {
        this.router.navigate(['/users'])
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  private convertToArrayRoles(receivedUserBody : any): String[] {
    let array: String[] = []
    Object.keys(receivedUserBody.traits).forEach(element => {
      if(receivedUserBody.traits[element] === true)
      array.push(element.toUpperCase())
    });
    console.log(array)
    return array
  }

}
