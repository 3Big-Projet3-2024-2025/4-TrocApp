import { Routes } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';
import { ViewingItemsMapComponent } from './viewing-items-map/viewing-items-map.component';
import { DetailedViewItemComponent } from './detailed-view-item/detailed-view-item.component';
import { UserEditComponent } from './user-edit/user-edit.component';

export const routes: Routes = [

    //Path for user management as admin
    { path: "users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
    //Path for viewing items on a map
    { path: "/viewing-items-map", component: ViewingItemsMapComponent },
    //Path for viewing a detailed item
    { path: "/detailed-view-item", component: DetailedViewItemComponent }
    { path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    
];
