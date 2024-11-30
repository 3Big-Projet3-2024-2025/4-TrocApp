import { Routes } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';
export const routes: Routes = [

    //Path for user management as admin
    { path: "users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
];
