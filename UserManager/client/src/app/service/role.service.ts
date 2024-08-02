import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Role } from '../entity/role';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: "root",
})

export class RoleService {

  constructor(private authService: AuthService, private httpClient: HttpClient) {
    console.log('*** RoleService()');
  }

  public findAll(): Observable<Role[]> {
    
    console.log('*** RoleService.findAll()');

    let rolesUrl = 'http://localhost:8080/server/roles';

    const headers = this.authService.getHeaders();

    return this.httpClient.get<Role[]>(rolesUrl, { headers });
  }
}