import { Component, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/User';
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent implements OnInit {
  @Input() user: User
  @Output() editModalTrigger = new EventEmitter()
  @Output() deleteModalTrigger = new EventEmitter()

  constructor(public router: Router) { }

  ngOnInit(): void {    
  }

  editButtonClicked() {
    this.router.navigate([`edit/${this.user.id}`])
    console.log('Edit button has been clicked! (user-card)')
    console.log('Emitting user: ', this.user )
  }

  deleteButtonClicked() {
    this.deleteModalTrigger.emit(this.user)
    console.log('Delete button has been clicked! (user-card)')
    console.log('Deleting user at id: ', this.user.id)
  }


}
