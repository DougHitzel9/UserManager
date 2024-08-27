import { NgFor, NgIf } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Role } from '../../entity/role';
import { User } from '../../entity/user';
import { AppComponent } from '../../app.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { UserDialogComponent } from '../user-dialog/user-dialog.component';
import { AuthService } from '../../service/auth.service';
import { RoleService } from '../../service/role.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [ ConfirmDialogComponent, NgbModule, NgFor, NgIf, RouterLink, UserDialogComponent ],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})

export class UserListComponent {

  @ViewChild(ConfirmDialogComponent) myConfirmDialog: ConfirmDialogComponent;
  @ViewChild(UserDialogComponent) myUserDialog: UserDialogComponent;

  public dialogTitle: string;

  public roles: Role[];
  public users: User[];

  public selectedUser: User;

  public confirmText = 'User will be deleted.';

  public isWaiting = false;

  constructor(private authService: AuthService,
              private roleService: RoleService,
              private userService: UserService,
              private appComponent: AppComponent) {
    console.log('*** UserListComponent');
    this.getRoles();
    this.getUsers();
  }

  public getRoles() {
    this.showOverlay();

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

  public getUsers() {
    this.showOverlay();

    this.userService.findAll().subscribe({
      next: data => {
        this.users = data;
        this.hideOverlay();
      },
      error: err => {
        this.handleError(err);
      }
    }); 
  }

  public addUser() {
    // assign new User to this.selectedUser
    this.selectedUser = new User;
    console.log('addUser() - selectedUser: ' + this.selectedUser.userId + ' : ' + this.selectedUser.username + ' : ' + this.selectedUser.role.roleId + ' : ' + this.selectedUser.role.role);
    this.dialogTitle = 'Add User';
    this.myUserDialog?.showModal();
  }

  public deleteUser(userId: number) {
    console.log("deleteUser() - " + userId);

    // deep clone
    this.selectedUser = new User;
    this.selectedUser.userId = userId;

    this.myConfirmDialog?.showModal();
  }

  public editUser(user: User) {
    // deep clone
    this.selectedUser = new User;
    this.selectedUser.transaction = 'E';
    this.selectedUser.userId = user.userId;
    this.selectedUser.username = user.username;
    this.selectedUser.role.roleId = user.role.roleId;
    this.selectedUser.role.role = user.role.role;

    this.dialogTitle = 'Edit User';

    this.myUserDialog?.showModal();
  }

  public handleConfirmDeleteEvent(confirm: boolean) {
    console.log('*** handleConfirmEvent() - confirm: ' + confirm);

    if (confirm) {
      this.showOverlay();

      let userId = this.selectedUser.userId;

      this.userService.deleteById(userId).subscribe({
        next: data => {
          // delete selected user
          let index = this.users.findIndex(row => row.userId === userId);

          this.users.splice(index, 1);
          this.hideOverlay();
        },
        error: err => {
          this.handleError(err);
        }
      });
    }
  }

  public handleSaveEvent(user: User) {
    console.log('*** handleConfirmEvent() - user: ' + user.userId + ' : transaction: ' + user.transaction);

    if (user.transaction === 'A') {
      this.users.push(user);
    } else {
      // update selected user
      let index = this.users.findIndex(row => row.userId === user.userId);

      this.users.splice(index, 1, user);
    }
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