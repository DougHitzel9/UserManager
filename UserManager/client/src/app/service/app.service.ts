import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: "root",
})

export class AppService {

  constructor(private authService: AuthService, private httpClient: HttpClient) {}

  public reset(): Observable<any> {
    
    console.log('*** AppService.reset()');

    let resetUrl = 'http://localhost:8080/server/reset';

    const headers = this.authService.getHeaders();

    return this.httpClient.post<any>(resetUrl, {}, { headers });
  }
}