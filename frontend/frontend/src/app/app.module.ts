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
import { EditModalComponent } from './components/editmodal/editmodal.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { DeleteModalComponent } from './components/deletemodal/deletemodal.component';
import { AddModalComponent } from './components/addmodal/addmodal.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { RouterModule } from '@angular/router';
import { routingComponents } from './app-routing.module';
import { UsersComponent } from './users/users.component';
import {MatRadioModule} from '@angular/material/radio';
import { UserDetailsComponent } from './components/user-details/user-details.component';

@NgModule({
  declarations: [
    AppComponent,
    UserCardComponent,
    EditModalComponent,
    DeleteModalComponent,
    AddModalComponent,
    HeaderComponent,
    HomeComponent,
    NotFoundComponent,
    routingComponents,
    UsersComponent,
    UsersComponent,
    UserDetailsComponent,
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
    MatIconModule,
    RouterModule,
    MatRadioModule

  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
