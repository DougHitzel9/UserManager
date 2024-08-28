import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from '../../app.component';
import { Credentials } from '../../entity/credentials';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ FormsModule, NgIf ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent implements OnInit {

  public credentials: Credentials = { username: 'Beta', password: 'Beta' };
  public attempts = 0;

  constructor(private appComponent: AppComponent, private authService: AuthService) {}

  ngOnInit() {
    console.log('*** login.ngOnInit()');
  }
  onSubmit() {
    console.log("onSubmit()");

    console.log('Username:', this.credentials.username);
    console.log('Password:', this.credentials.password);

    this.appComponent.showOverlay();
    this.authService.authenticate(this.credentials).subscribe({
      next: () => {
                // jwt is handled in authService
                this.appComponent.hideOverlay();
      },
      error: err => {
        this.attempts++;
        console.log("Status: " + err.status + " Attempts: " + this.attempts);
        this.appComponent.hideOverlay();
      }
    });
  }
}
