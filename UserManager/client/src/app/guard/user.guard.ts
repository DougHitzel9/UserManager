import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateChildFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root'
})

export class UserGuard {

  constructor(private authService: AuthService, private router: Router) {}

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('*** UserGuard.canActivateChild()');
    // Allow access to the '' route for any user
    if (route.routeConfig?.path === '') {
      return true;
    } else {
      this.router.navigate(['/access-denied']);
      return false;
    }
  }
}

export const canActivateChild: CanActivateChildFn =
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        console.log('*** UserGuard.canActivateChildGuard()');
        if (route.routeConfig?.path === '') {
            return true;
          } else {
            inject(Router).navigate(['/access-denied']);
            return false;
          }      
    };