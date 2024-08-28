import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { User } from '../entity/user';

@Injectable({
  providedIn: "root",
})

export class UserService {

  constructor(private authService: AuthService, private httpClient: HttpClient) {
    console.log('*** UserService()');
  }

  public deleteById(id: number):Observable<any> {
    
    console.log('*** UserService.deleteById()');

    let usersUrl = 'http://localhost:8080/server/jpa/users/' + id;

    const headers = this.authService.getHeaders();

    return this.httpClient.delete<User[]>(usersUrl, { headers });
  }

  public findAll(): Observable<User[]> {
    
    console.log('*** UserService.findAll()');

    let usersUrl = 'http://localhost:8080/server/jpa/users';

    const headers = this.authService.getHeaders();

    return this.httpClient.get<User[]>(usersUrl, { headers });
  }

  public findById(id: number): Observable<User[]> {
    
    console.log('*** UserService.findById()');

    let usersUrl = 'http://localhost:8080/server/jpa/users/' + id;

    const headers = this.authService.getHeaders();

    return this.httpClient.get<User[]>(usersUrl, { headers });
  }

  public save(user: User): Observable<User> {

    console.log('*** UserService.save() - ' + user.username);

    let usersUrl = 'http://localhost:8080/server/jpa/users';

    const headers = this.authService.getHeaders();

    return  this.httpClient.post<User>(usersUrl, user, { headers });
  }
}