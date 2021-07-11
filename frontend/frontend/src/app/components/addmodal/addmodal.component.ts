import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
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
      username: new FormControl(null),
      fullName:  new FormControl(null),
      email: new FormControl(null),
      password: new FormControl(null),
      roles: this.fb.group({
        user: false,
        administrator: false
      })
    })
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
