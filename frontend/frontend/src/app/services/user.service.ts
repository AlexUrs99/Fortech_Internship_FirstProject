import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'
import { User } from '../User'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[]> {
    console.log(`${this.baseUrl}/api/form/users`);
    return this.http.get<User[]>(`${this.baseUrl}/api/form/users`);
  }

  public getUserAtId(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/api/form/users/${userId}`);
  }

  public createUser(userBody: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/api/form/users`, userBody);
  }

  public editUser(userBody: User, userId: number): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/api/form/users/${userId}`, userBody);
  }

  public deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/api/form/users/${userId}`);
  }
}
