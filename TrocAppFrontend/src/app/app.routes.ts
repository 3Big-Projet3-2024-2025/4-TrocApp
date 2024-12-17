import { Routes } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';
import { UserEditComponent } from './user-edit/user-edit.component';

export const routes: Routes = [

    //Path for user management as admin
    { path: "users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
    { path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    
];
