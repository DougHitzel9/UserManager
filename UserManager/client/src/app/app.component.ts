import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './component/login/login.component';
import { AppService } from './service/app.service';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ LoginComponent, NgbModule, NgIf, RouterLink, RouterModule, RouterOutlet ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent {
  title = 'User Manager';

  public waiting = false;

  constructor(private appService: AppService, private authService: AuthService, private router: Router) {}

  public isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  public logout() {
    this.authService.clearJwt();
  }

  public hideOverlay() {
    this.waiting = false;
  }

  public showOverlay() {
    this.waiting = true;
  }

  public reset() {
    this.showOverlay();

    console.log('reset() A');

    this.appService.reset().subscribe( data => {
      console.log('reset() B');

      this.hideOverlay();
    }); 
  }
}
