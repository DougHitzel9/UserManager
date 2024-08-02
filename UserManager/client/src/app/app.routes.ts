import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { RoleListComponent } from './component/role-list/role-list.component';
import { UserListComponent } from './component/user-list/user-list.component';
import { canActivateGuard } from './guard/auth.guard';

export const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'list-users',
        component: UserListComponent,
        canActivate: [canActivateGuard]
    },
    {
        path: 'list-roles',
        component: RoleListComponent,
        canActivate: [canActivateGuard]
    }
];