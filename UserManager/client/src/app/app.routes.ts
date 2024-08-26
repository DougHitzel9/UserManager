import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
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
        path: 'list-roles',
        component: RoleListComponent,
        canActivate: [canActivateGuard]
    },
    {
        path: 'list-users',
        component: UserListComponent,
        canActivate: [canActivateGuard]
    }
];