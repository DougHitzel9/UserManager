import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { Credentials } from '../entity/credentials';

const JWT = 'jwt';

@Injectable({
  providedIn: 'root'
})

/**
 * localStorage maintains objects until specifically removed or cache is cleared
 * sessionStorage maintains objects until specifically removed or browser/tab is closed
 */
export class AuthService {

  constructor(private httpClient: HttpClient, private router : Router) {}

  public isAuthenticated(): boolean {
    // double-negate existence of JWT
    return !!sessionStorage.getItem(JWT);
  }

  public authenticate(credentials: Credentials): Observable<any> {
    let authenticateUrl = 'http://localhost:8080/server/authenticate';

    const username = 'Beta';
    const password = 'Beta';

    return this.httpClient.post<any>(authenticateUrl, credentials, {})
                .pipe(
                  map(
                    jwt => {
                      sessionStorage.setItem(JWT, jwt.token);
                      console.log("jwt: " + jwt.token);
                      this.router.navigate(['/']);
                    }
                  )
                );
  }

  public clearJwt() {
    sessionStorage.removeItem(JWT);
  }

  public getHeaders(): any {
    let jwt = sessionStorage.getItem(JWT);

    let headers = { 'Authorization': 'Bearer ' + jwt };

    return headers;
  }
}