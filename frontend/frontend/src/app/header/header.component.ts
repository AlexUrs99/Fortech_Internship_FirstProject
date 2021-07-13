import { Component, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Output() onAddButtonClicked = new EventEmitter()

  constructor(public router: Router) { }

  ngOnInit(): void {
  }

  toggleAddModal() {
    this.onAddButtonClicked.emit()
  }

}
