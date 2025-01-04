import { Routes } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { EditProfilComponent } from './edit-profil/edit-profil.component';
import { ViewingItemsMapComponent } from './viewing-items-map/viewing-items-map.component';
import { DetailedViewItemComponent } from './detailed-view-item/detailed-view-item.component';
import { ItemFormComponent } from './item-form/item-form.component';
//import { ItemtestComponent } from './itemtest/itemtest.component';
//import { ItemDetTestComponent } from './item-det-test/item-det-test.component';
//import { ExchangeListComponent } from './exchange-list/exchange-list.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

export const routes: Routes = [
    { path: '', component: ViewingItemsMapComponent }, // Default Path (Home Page)
    //Path for user management as admin
    { path: "users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
    //Path for viewing items on a map
    { path: "viewing-items-map", component: ViewingItemsMapComponent },
    //Path for viewing a detailed item
    { path: "detailed-view-item", component: DetailedViewItemComponent },
    //{ path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    {path: 'category-list', component: CategoryListComponent},
    //{ path: '**', redirectTo: '/category-list', pathMatch: 'full' },
    
    { path: "detailed-view-item/:id", component: DetailedViewItemComponent },
    //path for adding an item
    { path: "item", component: ItemFormComponent },
    //path for updating an item
    { path: "item/:id", component: ItemFormComponent },
    //path by default 
    { path: "", redirectTo: "/viewing-items-map", pathMatch: "full" },

    // { path: "itemDetTest/:id", component: ItemDetTestComponent },
    // {path: "exchanges", component: ExchangeListComponent },
    // { path: "itemtest", component: ItemtestComponent }
  
    // Path for editing a user
    { path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    { path: 'user-profile/:id', component: UserProfileComponent },

    
    { path: "users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
    { path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    { path: 'edit-profil', component: EditProfilComponent, /*canActivate: [authGuard]*/ },
    { path: '**', redirectTo: 'viewing-items-map', pathMatch: 'full' }, // Wildcard route
    
];
