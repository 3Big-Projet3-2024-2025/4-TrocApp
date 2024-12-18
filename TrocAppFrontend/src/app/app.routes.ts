import { Routes } from '@angular/router';
import { UsersManagementComponent } from './users-management/users-management.component';
import { ViewingItemsMapComponent } from './viewing-items-map/viewing-items-map.component';
import { DetailedViewItemComponent } from './detailed-view-item/detailed-view-item.component';
import { UserEditComponent } from './user-edit/user-edit.component';

import { ItemFormComponent } from './item-form/item-form.component';
import { ItemtestComponent } from './itemtest/itemtest.component';
import { ItemDetTestComponent } from './item-det-test/item-det-test.component';
import { ExchangeListComponent } from './exchange-list/exchange-list.component';
export const routes: Routes = [

    //Path for user management as admin
    { path: "admin/users-management", component: UsersManagementComponent, /*canActivate: [authGuard]*/ },
    //Path for viewing items on a map
    { path: "viewing-items-map", component: ViewingItemsMapComponent },
    //Path for viewing a detailed item
    { path: "detailed-view-item", component: DetailedViewItemComponent },
    { path: 'edit/:id', component: UserEditComponent, /*canActivate: [authGuard]*/ },
    
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
    // { path: '', component: ViewingItemsMapComponent }, // Page d'accueil
];
