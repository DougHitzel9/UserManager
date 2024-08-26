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

  public findAll(): Observable<User[]> {
    
    console.log('*** UserService.findAll()');

    let usersUrl = 'http://localhost:8080/server/users';

    const headers = this.authService.getHeaders();

    return this.httpClient.get<User[]>(usersUrl, { headers });
  }

  public findById(id: string): Observable<User[]> {
    
    console.log('*** UserService.findById()');

    let userId: number = parseInt(id);

    let usersUrl = 'http://localhost:8080/server/user/id';

    const headers = this.authService.getHeaders();

    return this.httpClient.get<User[]>(usersUrl, { headers });
  }

  public save(user: User): Observable<User> {

    console.log('*** UserService.save() - ' + user.username);

    let userUrl = 'http://localhost:8080/server/user';

    const headers = this.authService.getHeaders();

    return  this.httpClient.post<User>(userUrl, user, { headers });
  }
}