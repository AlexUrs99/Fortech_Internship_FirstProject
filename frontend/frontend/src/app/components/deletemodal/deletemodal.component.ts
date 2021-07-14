import { EventEmitter, Input } from '@angular/core';
import { Component, OnInit, Output } from '@angular/core';
import { VirtualTimeScheduler } from 'rxjs';
import { User } from 'src/app/User';


@Component({
  selector: 'app-deletemodal',
  templateUrl: './deletemodal.component.html',
  styleUrls: ['./deletemodal.component.css']
})
export class DeleteModalComponent implements OnInit {
  @Input() toDeleteUser: User
  @Output() onDeleteButton = new EventEmitter()
  @Output() what = new EventEmitter<User>()
  

  constructor() { }

  ngOnInit(): void {
    console.log('yo im toggled wtf')
  }

  toggleDeleteModal() {
    this.onDeleteButton.emit()
  }

  deleteRequestAccepted() {
    this.what.emit(this.toDeleteUser)
  }


}
