import { NgFor } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../../app.component';
import { Role } from '../../entity/role';
import { AuthService } from '../../service/auth.service';
import { RoleService } from '../../service/role.service';
import { InfoDialogComponent } from '../info-dialog/info-dialog.component';

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [ InfoDialogComponent, NgFor ],
  templateUrl: './role-list.component.html',
  styleUrl: './role-list.component.scss'
})

export class RoleListComponent {

  @ViewChild(InfoDialogComponent) myInfoDialog: InfoDialogComponent;

  public roles: Role[];

  public infoText = 'To be implemented...';

  constructor(private authService: AuthService,
              private roleService: RoleService,
              private appComponent: AppComponent,
              private router: Router) {

    this.getRoles();
  }

  public getRoles() {
    this.roleService.findAll().subscribe({
      next: data => {
        this.roles = data;
        this.hideOverlay();
      },
      error: err => {
        this.handleError(err);
      }
    });
  }
  
  public editRole(role: Role) {
    this.myInfoDialog?.showModal();
  }

  private handleError(err: any) {
    this.hideOverlay();

    console.log("Status: " + err.status);
    if (err.status === 403) {
      console.log("Status: " + err.status + " - jwt expired.");
      this.authService.clearJwt();
    }
  }

  private hideOverlay() {
    this.appComponent.hideOverlay();
  }

  private showOverlay() {
    this.appComponent.showOverlay();
  }
}