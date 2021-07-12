import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { UserCardComponent } from './components/user-card/user-card.component';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },

  {
    path: 'users',
    component: UsersComponent
  },

  {
    path: 'users/:username',
    component: UserCardComponent
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
