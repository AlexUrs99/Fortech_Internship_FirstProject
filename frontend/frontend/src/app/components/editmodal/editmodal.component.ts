import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/User';
import { EventEmitter } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-editmodal',
  templateUrl: './editmodal.component.html',
  styleUrls: ['./editmodal.component.css']
})
export class EditModalComponent implements OnInit {
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
  editForm: FormGroup =  this.fb.group({
    username: new FormControl(this.myUser.username),
    fullName: new FormControl(this.myUser.fullName),
    email: new FormControl(this.myUser.email),
    password: new FormControl(this.myUser.password),
    traits: this.fb.group({
      courageous: this.myUser.traits.includes('COURAGEOUS'),
      focused: this.myUser.traits.includes('FOCUSED'),
      caring: this.myUser.traits.includes('CARING'),
      perfectionist: this.myUser.traits.includes('PERFECTIONIST')
    }),
    gender: new FormControl(this.myUser.gender)
  })



  constructor(private fb: FormBuilder, public router: Router, private userService: UserService, private route:ActivatedRoute) { 

  }

  ngOnInit(): void {
    this.route.paramMap
    .subscribe(params => {
      this.myUserId = Number(params.get('id'))

      this.userService.getUserAtId(this.myUserId.valueOf()).subscribe((u) => {
        this.myUser = u;

        this.editForm = this.fb.group({
          username: new FormControl(this.myUser.username),
          fullName: new FormControl(this.myUser.fullName),
          email: new FormControl(this.myUser.email),
          password: new FormControl(this.myUser.password),
          traits: this.fb.group({
            courageous: this.myUser.traits.includes('COURAGEOUS'),
            focused: this.myUser.traits.includes('FOCUSED'),
            caring: this.myUser.traits.includes('CARING'),
            perfectionist: this.myUser.traits.includes('PERFECTIONIST')
          }),
          gender: new FormControl(this.myUser.gender)
        })
      },
      (error : HttpErrorResponse) => 
        alert(error.message)
      )
    })
    

  }

  private convertToArrayRoles(receivedUserBody: any): String[] {
    let array: String[] = []
    Object.keys(receivedUserBody.traits).forEach(element => {
      if (receivedUserBody.traits[element] === true)
        array.push(element.toUpperCase())
    });
    console.log(array)
    return array
  }

  onSubmit() {
    const userBody: User = this.editForm.value
    console.log(this.myUserId)

    userBody.traits = this.convertToArrayRoles(userBody)

    this.userService.editUser(userBody, +this.myUserId).subscribe(
      (response: User) => {
        this.router.navigate(['/users'])
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
}

