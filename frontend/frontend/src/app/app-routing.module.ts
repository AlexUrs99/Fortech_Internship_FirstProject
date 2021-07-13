import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddModalComponent } from './components/addmodal/addmodal.component';
import { EditModalComponent } from './components/editmodal/editmodal.component';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { UserCardComponent } from './components/user-card/user-card.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },

  {
    path: 'details/:id',
    component: UserDetailsComponent

  },

  {
    path: 'add',
    component: AddModalComponent
  },

  {
    path: 'edit/:id',
    component:EditModalComponent
  },

  {
    path: 'users',
    component: UsersComponent
  },

  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routingComponents = [
  UsersComponent,
  HomeComponent,
  NotFoundComponent,
  UserCardComponent
]
