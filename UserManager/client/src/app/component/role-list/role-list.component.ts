import { NgFor } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { Role } from '../../entity/role';
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

  constructor(private roleService: RoleService) {
    console.log('*** RoleListComponent');
    this.getRoles();
  }

  public getRoles() {
    console.log('*** RoleListComponent.getRoles()');
    
    this.roleService.findAll().subscribe(data => {
      this.roles = data;
    }); 
  }
  
  public editRole(role: Role) {
    console.log('editRole() - selectedRole: ' + role.roleId + ' : ' + role.role);
    
    this.myInfoDialog?.showModal();
  }
}
