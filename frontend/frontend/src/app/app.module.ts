import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserCardComponent } from './components/user-card/user-card.component';
import { UserService } from './services/user.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { EditmodalComponent } from './components/editmodal/editmodal.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { DeletemodalComponent } from './components/deletemodal/deletemodal.component';
import { AddmodalComponent } from './components/addmodal/addmodal.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon'; 
import { HeaderComponent } from './header/header.component'; 


@NgModule({
  declarations: [
    AppComponent,
    UserCardComponent,
    EditmodalComponent,
    DeletemodalComponent,
    AddmodalComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDividerModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCheckboxModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule

  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
