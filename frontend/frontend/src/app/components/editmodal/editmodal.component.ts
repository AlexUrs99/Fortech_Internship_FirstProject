import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/User';
import { EventEmitter } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-editmodal',
  templateUrl: './editmodal.component.html',
  styleUrls: ['./editmodal.component.css']
})
export class EditmodalComponent implements OnInit {
  @Input() editedUser: User;
  @Output() onCloseEditModal = new EventEmitter()
  @Output() onEditFormSubmitted = new EventEmitter<User>()
  editForm: FormGroup;
  

  constructor(private fb : FormBuilder) { }

  ngOnInit(): void {

    this.editForm = this.fb.group({
      username: new FormControl(this.editedUser.username),
      fullName:  new FormControl(this.editedUser.fullName),
      email: new FormControl(this.editedUser.email),
      password: new FormControl(this.editedUser.password),
      traits: this.fb.group({
        courageous: this.editedUser.traits.includes('COURAGEOUS'),
        focused: this.editedUser.traits.includes('FOCUSED'),
        caring: this.editedUser.traits.includes('CARING'),
        perfectionist: this.editedUser.traits.includes('PERFECTIONIST')
      }),
      gender: new FormControl(this.editedUser.gender)
    })
  }

  closeEditModal() {
    this.onCloseEditModal.emit()
  }

  onSubmit() {
    const userBody : User = this.editForm.value
    // const userId: number = this.editedUser.id
    // console.log(userBody);
    this.onEditFormSubmitted.emit(userBody)
  }

}
