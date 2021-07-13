import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/User';

@Component({
  selector: 'app-addmodal',
  templateUrl: './addmodal.component.html',
  styleUrls: ['./addmodal.component.css']
})
export class AddmodalComponent implements OnInit {
  addForm: FormGroup;
  @Output() onCloseAdd = new EventEmitter()
  @Output() onAddFormSubmitted = new EventEmitter()

  constructor(private fb : FormBuilder) { }

  ngOnInit(): void {
    this.addForm = this.fb.group({
      username: new FormControl(null, [
        Validators.required
      ]),
      fullName:  new FormControl(null),
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
      })
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

  get roles() {
    return this.addForm.get('traits')
  }

  closeAddModal() {
    this.onCloseAdd.emit()
  }

  onSubmit() {
    const userBody : User = this.addForm.value
    // const userId: number = this.editedUser.id
    // console.log(userBody);
    this.onAddFormSubmitted.emit(userBody)
  }

}
