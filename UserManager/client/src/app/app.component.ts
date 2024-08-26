import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationStart, Router, RouterEvent, RouterLink, RouterModule, RouterOutlet } from '@angular/router';
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

export class AppComponent implements OnInit {
  title = 'User Manager';

  public waiting = false;

  constructor(private appService: AppService, private authService: AuthService,
              private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        // verify user is authenticated
        if(!this.authService.isAuthenticated()){
          this.router.navigate(['login']);
        }
      }
    });

    this.activatedRoute.queryParams.subscribe(params => {
      let route = params['route'];

      if (!!route) {
        // browser has refreshed
        console.log("*** ngOnInit() - route: " + route);
        this.router.navigate([route]);
      }
    });
  }

  public isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  public logout() {
    console.log('logout() - A');
    this.authService.clearJwt();
    console.log('logout() - B');
  }

  public hideOverlay() {
    this.waiting = false;
  }

  public showOverlay() {
    this.waiting = true;
  }

  public reset() {
    this.showOverlay();

    this.appService.reset().subscribe( data => {
      // reload page
      location.reload();

      this.hideOverlay();
    }); 
  }
}
