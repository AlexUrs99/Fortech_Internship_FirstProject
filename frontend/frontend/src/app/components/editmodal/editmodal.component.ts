import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ROLES } from 'src/app/mock-roles';
import { User } from 'src/app/User';

@Component({
  selector: 'app-editmodal',
  templateUrl: './editmodal.component.html',
  styleUrls: ['./editmodal.component.css']
})
export class EditmodalComponent implements OnInit {
  @Input() editedUser: User;

  isShowingModal : boolean = false
  editForm: FormGroup;
  rolesOptions = ROLES;
  
  

  constructor(private builder : FormBuilder) { }

  ngOnInit(): void {
    console.log(this.editedUser);
    this.isShowingModal = true;
    this.editForm = this.builder.group({
      username: '',
      fullName: '',
      email: '',
      password: '',
      roles: '',
      gender: ''
    })

    this.editForm.valueChanges.subscribe(console.log);
  }

  onSubmit() {
    console.log('submitted')
  }

}
